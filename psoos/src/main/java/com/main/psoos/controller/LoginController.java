package com.main.psoos.controller;

import com.main.psoos.dto.ClientDTO;
import com.main.psoos.dto.CsvDTO;
import com.main.psoos.dto.CustomerDTO;
import com.main.psoos.dto.DocumentDTO;
import com.main.psoos.dto.LoginDTO;
import com.main.psoos.dto.MugDTO;
import com.main.psoos.dto.MugDesignDTO;
import com.main.psoos.dto.OrderDTO;
import com.main.psoos.dto.ShirtDTO;
import com.main.psoos.dto.ShirtDesignDTO;
import com.main.psoos.dto.UserDTO;
import com.main.psoos.model.Customer;
import com.main.psoos.model.MugDesign;
import com.main.psoos.model.Order;
import com.main.psoos.model.ShirtDesign;
import com.main.psoos.model.User;
import com.main.psoos.service.CsvService;
import com.main.psoos.service.CustomMultipartFile;
import com.main.psoos.service.CustomerService;
import com.main.psoos.service.DocumentService;
import com.main.psoos.service.MugDesignService;
import com.main.psoos.service.MugService;
import com.main.psoos.service.OrderService;
import com.main.psoos.service.ShirtDesignService;
import com.main.psoos.service.ShirtService;
import com.main.psoos.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class LoginController {
    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    DocumentService documentService;

    @Autowired
    OrderService orderService;

    @Autowired
    MugService mugService;

    @Autowired
    ShirtService shirtService;

    @Autowired
    ShirtDesignService shirtDesignService;

    @Autowired
    MugDesignService mugDesignService;

    @Autowired
    CsvService csvService;

    List<DocumentDTO> documentOrders = new ArrayList<>();
    List<MugDTO> mugOrders = new ArrayList<>();
    List<ShirtDTO> shirtOrders = new ArrayList<>();
    List<OrderDTO> orders = new ArrayList<>();
    private Customer loggedCustomer = new Customer();
    private User loggedUser = new User();
    private Map<String, Object> model = new HashMap<>();

    private final String SHIRT_DESIGN_PATH = "C:\\Users\\Axel\\Downloads\\PSOOS_new\\psoos\\src\\main\\resources\\static\\img\\T-Shirt Designs\\";
    private final String MUG_DESIGN_PATH = "C:\\Users\\Axel\\Downloads\\PSOOS_new\\psoos\\src\\main\\resources\\static\\img\\Mugs Designs\\";

    @GetMapping("/loginPage")
    public String loginPage(Model model){
        model.addAttribute("isSuccess", true);
        return "index";
    }

    @GetMapping("/accountPage")
    public String accountPage(Model model, User user){
        Customer customer = customerService.getCustomerByName(user.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("customer", customer);
        model.addAttribute("password", user.getPassword());
        return "account";
    }

    @GetMapping("/adminPage")
    public String adminPage(Model model, User user){
        Customer customer = customerService.getCustomerByName(user.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("customer", customer);
        orders.clear();
        if(orders.isEmpty()) {
            orderService.getAllOrders().stream().
                    filter(order -> order.getStatus().equals("CREATED") || order.getStatus().equals("")|| order.getStatus() == null).
                        forEach(order -> {
                            OrderDTO orderDTO = new OrderDTO(order);
                            orderDTO.setMugDTOS(mugService.getMugDTOByJoId(orderDTO.getJoId()));
                            orderDTO.setDocumentDTOS(documentService.getDocumentDTOByJoId(orderDTO.getJoId()));
                            orderDTO.setShirtDTOS(shirtService.getShirtDTOByJoId(orderDTO.getJoId()));
                            orders.add(orderDTO);
                        });

        }
        model.addAttribute("orders", orders);

        List<String> workers = userService.getAllActiveUsers().stream()
                .filter(tempUser -> tempUser.getName().equals("Worker 1")
                        || tempUser.getName().equals("Worker 2")
                        || tempUser.getName().equals("Worker 3"))
                .map(User::getName)
                .toList();
        model.addAttribute("role", user.getRole());
        model.addAttribute("workers", workers);

        return "admin";
    }

    @GetMapping("adminManageAccounts")
    public String getAdminManageAccounts(Model model, User user){
        Customer customer = customerService.getCustomerByName(user.getName());
        model.addAttribute("name", loggedUser.getName());
        model.addAttribute("customer", customer);
        List<User> users = userService.getAllActiveUsers();
        List<UserDTO> usersDTO = users.stream().map(UserDTO::new).toList();

        List<ClientDTO> clientDTOS = new ArrayList<>();
        usersDTO.forEach(userDTO -> {
            ClientDTO client = new ClientDTO();
            client.setName(userDTO.getName());
            client.setPassword(userDTO.getPassword());
            client.setUserId(userDTO.getUserId());
            client.setDeleted(userDTO.getDeleted());
            client.setUserName(userDTO.getUserName());
            Customer tempCustomer = customerService.getCustomerByName(userDTO.getName());
            client.setCustomerDTO(new CustomerDTO(tempCustomer));
            clientDTOS.add(client);
        });

        model.addAttribute("clients", clientDTOS);
        model.addAttribute("password", loggedUser.getPassword());
        model.addAttribute("users", usersDTO);


        return "admin_manage_accounts";
    }

    @GetMapping("/adminDesignerPage")
    public String adminDesignerPage(Model model, User user){
        Customer customer = customerService.getCustomerByName(user.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("customer", customer);
        orders.clear();
        if(orders.isEmpty()) {
            orderService.getAllOrders().stream().
                    filter(order -> order.getStatus().equals("ACCEPTED") || order.getStatus().equals("")|| order.getStatus() == null).
                    forEach(order -> {
                        OrderDTO orderDTO = new OrderDTO(order);
                        orderDTO.setMugDTOS(mugService.getMugDTOByJoId(orderDTO.getJoId()));
                        orderDTO.setDocumentDTOS(documentService.getDocumentDTOByJoId(orderDTO.getJoId()));
                        orderDTO.setShirtDTOS(shirtService.getShirtDTOByJoId(orderDTO.getJoId()));
                        orders.add(orderDTO);
                    });
            orderService.getAllOrders().forEach(order -> {
                OrderDTO orderDTO = new OrderDTO(order);
                orderDTO.setMugDTOS(mugService.getMugDTOByJoId(orderDTO.getJoId()));
                orderDTO.setDocumentDTOS(documentService.getDocumentDTOByJoId(orderDTO.getJoId()));
                orderDTO.setShirtDTOS(shirtService.getShirtDTOByJoId(orderDTO.getJoId()));
                orders.add(orderDTO);
            });
        }
        model.addAttribute("orders", orders);

        return "admin_designer";
    }

    @GetMapping("/adminPendingOrdersPage")
    public String adminPendingOrdersPage(Model model, User user){
        Customer customer = customerService.getCustomerByName(user.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("customer", customer);
        orders.clear();
        orderService.getAllOrders().
                stream().
                    filter(order -> order.getStatus().equals("PENDING") || order.getStatus().equals("ACCEPTED") && order.getWorker().equals(loggedUser.getName())).
                        forEach(tempOrder -> {
                            OrderDTO orderDTO = new OrderDTO(tempOrder);
                                orderDTO.setMugDTOS(mugService.getMugDTOByJoId(orderDTO.getJoId()));
                                orderDTO.setDocumentDTOS(documentService.getDocumentDTOByJoId(orderDTO.getJoId()));
                                orderDTO.setShirtDTOS(shirtService.getShirtDTOByJoId(orderDTO.getJoId()));
                            orders.add(orderDTO);
            });
        model.addAttribute("orders", orders);

        return "admin_pending";
    }

    @GetMapping("/adminCompletedOrdersPage")
    public String adminCompletedOrdersPage(Model model, User user){
        Customer customer = customerService.getCustomerByName(user.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("customer", customer);
        orders.clear();
        if(orders.isEmpty()) {
            orderService.getAllOrders().
                    stream().
                    filter(order -> order.getStatus().equals("COMPLETED") || order.getStatus().equals("DECLINED") || order.getStatus().equals("DONE")).
                    forEach(tempOrder -> {
                        OrderDTO orderDTO = new OrderDTO(tempOrder);
                        orderDTO.setMugDTOS(mugService.getMugDTOByJoId(orderDTO.getJoId()));
                        orderDTO.setDocumentDTOS(documentService.getDocumentDTOByJoId(orderDTO.getJoId()));
                        orderDTO.setShirtDTOS(shirtService.getShirtDTOByJoId(orderDTO.getJoId()));
                        orders.add(orderDTO);
                    });
        }
        model.addAttribute("orders", orders);

        return "admin_completed";
    }


    @GetMapping("/registrationPage")
    public String registrationPage(){
        return "createAccount";
    }

    @PostMapping("/login")
    public String login(LoginDTO loginDTO, Model model){
        UserDTO userDTO = new UserDTO(loginDTO);
        User user = new User(userDTO);

        User tempUser = userService.loginUser(user);
        boolean isLoginCorrect = false;

        if(tempUser != null){
            isLoginCorrect = true;
        }
        boolean isSuccess = false;

        if(loginDTO.getEmail().equals("")){
            model.addAttribute("emailBlank",true);
        }

        switch(tempUser.getRole()){
            case "USER_ADMIN":
                loggedUser = tempUser;
                loggedCustomer = customerService.getCustomerByName(tempUser.getName());
                this.model.putAll(model.asMap());

                return adminPage(model, tempUser);
            case "USER_WORKER":
                loggedUser = tempUser;
                loggedCustomer = customerService.getCustomerByName(tempUser.getName());
                this.model.putAll(model.asMap());

                return adminPendingOrdersPage(model, tempUser);

            case "USER_CLIENT"  :
                isSuccess = true;
                loggedUser = tempUser;
                loggedCustomer = customerService.getCustomerByName(tempUser.getName());

                return accountPage(model, tempUser);
            case "USER_DESIGNER":
                loggedUser = tempUser;
                loggedCustomer = customerService.getCustomerByName(tempUser.getName());
                this.model.putAll(model.asMap());

                return adminDesignerPage(model, tempUser);
        }

        model.addAttribute("isSuccess", false);
        return "index";
    }

    @PostMapping({"/createAccount"})
    public String createAccount(CustomerDTO customerDTO, Model model) {
      System.out.println(customerDTO.getCustomerEmail() + customerDTO.getCustomerName());

        boolean isSuccess = true;
        if(customerDTO.getCustomerName().equals("")){
            model.addAttribute("nameBlank",true);
            isSuccess = false;
        }
        if(customerDTO.getCustomerName().equals("")){
            model.addAttribute("nameBlank",true);
            isSuccess = false;
        }

        if(isSuccess){
            Customer customer = new Customer(customerDTO);
            UserDTO userDTO = new UserDTO(customerDTO);
            User user = new User(userDTO);
            user.setRole("USER_CLIENT");
            userService.createUser(user);
            customerService.createCustomer(customer);
        }

        model.addAttribute("isSuccess",isSuccess);
        return "createAccount";
    }

    @PostMapping({"/createAccountAdmin"})
    public String createAccountAdmin(CustomerDTO customerDTO, Model model) {
        System.out.println(customerDTO.getCustomerEmail() + customerDTO.getCustomerName());

        boolean isSuccess = true;
        if(customerDTO.getCustomerName().equals("")){
            model.addAttribute("nameBlank",true);
            isSuccess = false;
        }
        if(customerDTO.getCustomerName().equals("")){
            model.addAttribute("nameBlank",true);
            isSuccess = false;
        }

        if(isSuccess){
            Customer customer = new Customer(customerDTO);
            UserDTO userDTO = new UserDTO(customerDTO);
            User user = new User(userDTO);
            user.setRole("USER_CLIENT");
            userService.createUser(user);
            customerService.createCustomer(customer);
        }

        model.addAttribute("isSuccess",isSuccess);
        return adminPage(model, loggedUser);
    }

    @PostMapping("/updateAccount")
    public String updateAccount(CustomerDTO customerDTO, Model model){
        Customer tempCustomer = customerService.getCustomerByName(customerDTO.getCustomerName());
        System.out.println(customerDTO.getCustomerPhoneNumber());
        tempCustomer.setCustomerPhoneNumber(customerDTO.getCustomerPhoneNumber());
        tempCustomer.setCustomerName(customerDTO.getCustomerName());
        tempCustomer.setCustomerEmail(customerDTO.getCustomerEmail());
        tempCustomer.setCustomerAddress(customerDTO.getCustomerHomeAddress());
        User tempUser = userService.getUserByName(tempCustomer.getCustomerName());

        //Updating of User and Customer Credentials
        userService.createUser(tempUser);
        customerService.updateCustomerDetails(tempCustomer);
        User user = userService.getUserByName(tempCustomer.getCustomerName());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("customer", tempCustomer);

        return accountPage(model, user);
    }

    @PostMapping("/updateAccountAdmin")
    public String updateAccountAdmin(CustomerDTO customerDTO, Model model){
        Customer tempCustomer = customerService.getCustomerByName(customerDTO.getCustomerName());
        System.out.println(customerDTO.getCustomerPhoneNumber());
        tempCustomer.setCustomerPhoneNumber(customerDTO.getCustomerPhoneNumber());
        tempCustomer.setCustomerName(customerDTO.getCustomerName());
        tempCustomer.setCustomerEmail(customerDTO.getCustomerEmail());
        tempCustomer.setCustomerAddress(customerDTO.getCustomerHomeAddress());
        User tempUser = userService.getUserByName(tempCustomer.getCustomerName());

        //Updating of User and Customer Credentials
        userService.createUser(tempUser);
        customerService.updateCustomerDetails(tempCustomer);
        User user = userService.getUserByName(tempCustomer.getCustomerName());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("customer", tempCustomer);

        return adminPage(model, loggedUser);
    }

    @GetMapping("/myOrdersPage")
    public String myOrdersPage(Model model){

        List<Order> customerOrders = new ArrayList<>();
        customerOrders = orderService.getOrdersById(loggedCustomer.getCustomerId());
        List<OrderDTO> customerOrderDTO = new ArrayList<>();
        customerOrders.forEach(order -> {
            customerOrderDTO.add(new OrderDTO(order));
        });
        model.addAttribute("orders", customerOrderDTO);
        return myOrdersPage(model);
    }

    @GetMapping("/homePage")
    public String homePage(){
        return "home";
    }

    @GetMapping("/uploadDocuments")
    public String uploadDocumentsPage(){
        return "uploadDocuments";
    }

    @GetMapping("/uploadDocuments_preDesign")
    public String uploadDocuments_preDesign(){
        return "uploadDocument_preDesign";
    }

    @GetMapping("/uploadMug")
    public String uploadMugPage(Model model){
        model.addAttribute("designs", getAllShirtDesigns());
        return "uploadMug";
    }

    @GetMapping("/uploadMug_preDesign")
    public String uploadMugPage_preDesign(){
        return "uploadMug_preDesign";
    }

    @GetMapping("/admin_designer")
    public String adminDesignerPage(){
        return "admin_designer";
    }

    @GetMapping("/uploadShirt")
    public String uploadShirtPage(Model model){
        model.addAttribute("designs", getAllShirtDesigns());

        return "uploadShirt";
    }

    @GetMapping("/uploadShirt_preDesign")
    public String uploadShirtPage_preDesign(){
        return "uploadShirt_preDesign";
    }

    @PostMapping("/addDocument")
    public String addDocument(DocumentDTO tempDocument, Model model) throws IOException {
        addDocumentOrder(tempDocument);
        model.addAttribute("orders", documentOrders);
        model.addAttribute("mugOrders", mugOrders);
        model.addAttribute("shirtOrders", shirtOrders);

        Integer totalPrice =  getTotalPrice(
                shirtOrders,
                documentOrders,
                mugOrders);

        model.addAttribute("totalPrice", totalPrice);
        return "cartOrders";
    }

    @PostMapping("/addMug")
    public String addMug(MugDTO mugDTO, Model model) throws IOException {
        addMugOrder(mugDTO);
        model.addAttribute("mugOrders", mugOrders);
        model.addAttribute("orders", documentOrders);
        model.addAttribute("shirtOrders", shirtOrders);

        Integer totalPrice =  getTotalPrice(
                shirtOrders,
                documentOrders,
                mugOrders);
        model.addAttribute("totalPrice", totalPrice);
        return "cartOrders";
    }

    @PostMapping("/addShirt")
    public String addShirt(ShirtDTO shirtDTO, Model model) throws IOException {
        addShirtOrder(shirtDTO);
        model.addAttribute("mugOrders", mugOrders);
        model.addAttribute("shirtOrders", shirtOrders);
        model.addAttribute("orders", documentOrders);
        Integer totalPrice =  getTotalPrice(
                shirtOrders,
                documentOrders,
                mugOrders);

        model.addAttribute("totalPrice", totalPrice);
        return "cartOrders";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(OrderDTO tempOrderDTO, Model model)throws IOException {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderStatus(tempOrderDTO.getOrderStatus());
        orderDTO.setWorker(tempOrderDTO.getWorker());
        //orderDTO.setWorkerNotes(tempOrderDTO.getWorkerNotes());

        orderDTO.setMugDTOS(mugOrders);
        orderDTO.setDocumentDTOS(documentOrders);
        orderDTO.setShirtDTOS(shirtOrders);
        orderDTO.setStatus("CREATED");
        orderDTO.setJoId("JO-" + Math.addExact(orderService.countOrder(), 1));
        Integer totalPrice = 0;

        orderDTO.setTotalPrice(getTotalPrice(
                orderDTO.getShirtDTOS(),
                orderDTO.getDocumentDTOS(),
                orderDTO.getMugDTOS())
        );

        setJoId(orderDTO.getJoId());
        orderDTO.setCustomerId(loggedCustomer.getCustomerId());
        //Saving of Data
        documentService.store(orderDTO.getDocumentDTOS());
        mugService.store(orderDTO.getMugDTOS());
        shirtService.store(orderDTO.getShirtDTOS());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int countLength = orderDTO.getJoId().substring(3).length();
        String updatedJO = orderDTO.getJoId().substring(3);
        if(countLength == 1){
            ImageIO.write(generateEAN13BarcodeImage("00000000000"+updatedJO), "png", baos);
        }else {
            ImageIO.write(generateEAN13BarcodeImage("0000000000"+updatedJO), "png", baos);
        }
        byte[] byteImage = baos.toByteArray();
        orderDTO.setBarcode(byteImage);

        orderService.saveOrders(new Order(orderDTO));

        List<Order> customerOrders = new ArrayList<>();
        customerOrders = orderService.getOrdersById(loggedCustomer.getCustomerId());
        List<OrderDTO> customerOrderDTO = new ArrayList<>();
        customerOrders.forEach(order -> {
            customerOrderDTO.add(new OrderDTO(order));
        });

        model.addAttribute("orders", customerOrderDTO);
        mugOrders = null;
        shirtOrders = null;
        documentOrders = null;
        return "myOrders";
    }

    public void setJoId(String JoId){

        for(DocumentDTO dto : documentOrders){
            dto.setJobOrder(JoId);
        }
        for(MugDTO dto : mugOrders){
            dto.setJobOrder(JoId);
        }
        for(ShirtDTO dto : shirtOrders){
            dto.setJobOrder(JoId);
        }
    }

    public void addMugOrder(MugDTO mugDTO) throws IOException {
        mugDTO.setOrderType("MUG");

            String uploadDir = "C:\\Users\\Axel\\Downloads\\" + loggedUser.getUserId();
            String fileName = StringUtils.cleanPath(mugDTO.getFile().getOriginalFilename());
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectory(uploadPath);
            }
            try{
                File file = new File(uploadDir +"\\" +fileName);
                String mimeType = Files.probeContentType(uploadPath);
                DiskFileItem fileItem = new DiskFileItem(fileName, mimeType, false, file.getName(), (int) file.length(), file.getParentFile());
                fileItem.getOutputStream();

                mugDTO.setFileToUpload(file);
                InputStream inputStream = mugDTO.getFile().getInputStream();
                Path filePath = uploadPath.resolve(fileName);
                CustomMultipartFile customMultipartFile = new CustomMultipartFile(filePath);
                mugDTO.setFile(customMultipartFile);
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                mugOrders.add(mugDTO);
            }catch(IOException e ){
                throw new IOException("ERROR IS: " + e.getMessage() + e.getLocalizedMessage() );
            }



    }
    public void addDocumentOrder(DocumentDTO tempDocument) throws IOException {
        tempDocument.setOrderType("DOCUMENT");


            String uploadDir = "C:\\Users\\Axel\\Downloads\\" + loggedUser.getUserId();
            String fileName = StringUtils.cleanPath(tempDocument.getFile().getOriginalFilename());
            Path uploadPath = Paths.get(uploadDir);

            if(!Files.exists(uploadPath)){
                Files.createDirectory(uploadPath);
            }
            try{
                File file = new File(uploadDir +"\\" +fileName);
                String mimeType = Files.probeContentType(uploadPath);
                DiskFileItem fileItem = new DiskFileItem(fileName, mimeType, false, file.getName(), (int) file.length(), file.getParentFile());
                fileItem.getOutputStream();

                tempDocument.setFileToUpload(file);
                InputStream inputStream = tempDocument.getFile().getInputStream();
                Path filePath = uploadPath.resolve(fileName);
                CustomMultipartFile customMultipartFile = new CustomMultipartFile(filePath);
                tempDocument.setFile(customMultipartFile);
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            }catch(IOException e ){
                throw new IOException("ERROR IS: " + e.getMessage() + e.getLocalizedMessage() );

        }
        documentOrders.add(tempDocument);
    }

    @GetMapping("/saveShirtDesigns")
    public String saveDocumentDesign() throws IOException {

        String uploadDir = "C:\\Users\\Axel\\Downloads\\T-Shirt Designs\\" ;
        String fileName = "T-shirt Designs - ";
        Path uploadPath = Paths.get(uploadDir);

        for(int i = 1; i <= 6; i++ ){
            fileName = "T-shirt Designs - ";
            ShirtDesignDTO dto = new ShirtDesignDTO();
            File file = new File(uploadDir +"\\" +fileName + i + ".png");
            fileName  = fileName + i+ ".png";
            dto.setName(file.getName());
            Path filePath = uploadPath.resolve(fileName);
            CustomMultipartFile customMultipartFile = new CustomMultipartFile(filePath);
            dto.setType(customMultipartFile.getContentType());

            ShirtDesign design = new ShirtDesign(dto);
            shirtDesignService.saveDesign(design);
            System.out.println("succesfulyy saved: " + design.getName());
            fileName = null;
            file = null;
        }

        return "succesful";
    }

    @GetMapping("/saveMugDesigns")
    public String saveMugDesign() throws IOException {

        String uploadDir = "C:\\Users\\Axel\\Downloads\\Mugs Designs\\" ;
        String fileName = "Mug Designs - ";
        Path uploadPath = Paths.get(uploadDir);

        for(int i = 1; i <= 6; i++ ){
            fileName = "Mug Designs - ";
            MugDesignDTO dto = new MugDesignDTO();
            File file = new File(uploadDir +"\\" +fileName + i + ".png");
            fileName  = fileName + i+ ".png";
            dto.setName(file.getName());
            Path filePath = uploadPath.resolve(fileName);
            CustomMultipartFile customMultipartFile = new CustomMultipartFile(filePath);
            dto.setType(customMultipartFile.getContentType());
            MugDesign design = new MugDesign(dto);
            mugDesignService.saveDesign(design);
            System.out.println("succesfulyy saved: " + design.getName());
            fileName = null;
            file = null;
        }

        return "succesful";
    }

    public void addShirtOrder(ShirtDTO tempShirt) throws IOException {
        tempShirt.setOrderType("SHIRT");

            if(tempShirt.getFile() == null){

                String customizedDesignName = tempShirt.getCustomizationType()+".png";
                File file = new File(SHIRT_DESIGN_PATH + customizedDesignName);
                Path downloadPath = Paths.get(SHIRT_DESIGN_PATH);
                String mimeType = Files.probeContentType(downloadPath);
                Path filePath = downloadPath.resolve(customizedDesignName);
                DiskFileItem fileItem = new DiskFileItem(customizedDesignName, mimeType, false, file.getName(), (int) file.length(), file.getParentFile());
                fileItem.getOutputStream();
                CustomMultipartFile customMultipartFile = new CustomMultipartFile(filePath);
                tempShirt.setFile(customMultipartFile);
            }else{
                try{
                    String uploadDir = "C:\\Users\\Axel\\Downloads\\" + loggedUser.getUserId();
                    String fileName = StringUtils.cleanPath(tempShirt.getFile().getOriginalFilename());
                    Path uploadPath = Paths.get(uploadDir);
                    if(!Files.exists(uploadPath)){
                        Files.createDirectory(uploadPath);
                    }
                    File file = new File(uploadDir +"\\" +fileName);
                    String mimeType = Files.probeContentType(uploadPath);
                    DiskFileItem fileItem = new DiskFileItem(fileName, mimeType, false, file.getName(), (int) file.length(), file.getParentFile());
                    fileItem.getOutputStream();
                    tempShirt.setFileToUpload(file);
                    InputStream inputStream = tempShirt.getFile().getInputStream();
                    Path filePath = uploadPath.resolve(fileName);
                    CustomMultipartFile customMultipartFile = new CustomMultipartFile(filePath);
                    tempShirt.setFile(customMultipartFile);
                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                }catch(IOException e ){
                    throw new IOException("ERROR IS: " + e.getMessage() + e.getLocalizedMessage() );
                }
            }
        shirtOrders.add(tempShirt);
    }

    @GetMapping("/pickCustomizations")
    public String pickCustomizationsPage(){
        return "WhatWouldYouLikeToCustomize";
    }

    @GetMapping("/pickOwnCustomizations")
    public String pickOwnCustomizationsPage(){
        return "WhatWouldYouLikeToCustomizeOwnDesign";
    }

    @GetMapping("/order/{status}/{jobId}")
    public String updateJobOrderStatusToOngoing(@PathVariable("status") String status, @PathVariable("jobId") String jobId, Model model){

        Order order = orderService.findByJobId(jobId);
        order.setStatus(status);
        orderService.saveOrders(order);

        return adminPage(model, loggedUser);
    }

    @GetMapping("/order/{status}/{jobId}/{worker}")
    public String updateJobOrderStatusToOngoing(@PathVariable("status") String status, @PathVariable("jobId") String jobId,
                                                @PathVariable("worker") String worker, Model model){

        Order order = orderService.findByJobId(jobId);
        order.setWorker(worker);
        order.setStatus(status);
        orderService.saveOrders(order);

        return adminPage(model, loggedUser);
    }



    @PostMapping("/order/{jobId}")
    public String updateJobOrderStatus(OrderDTO orderDTO, Model model){
        Order order = orderService.findByJobId(orderDTO.getJoId());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setWorkerNotes(orderDTO.getWorkerNotes());
        orderService.saveOrders(order);

        return adminPendingOrdersPage(model, loggedUser);
    }

    @GetMapping("/user/delete/{name}")
    public String deleteUser(@PathVariable("name") String name, Model model){
        User user = userService.getUserByName(name);
        user.setIsDeleted(true);
        userService.createUser(user);

        return getAdminManageAccounts(model, loggedUser);

    }

    public Integer getTotalPrice(List<ShirtDTO> shirts, List<DocumentDTO> documents, List<MugDTO> mugs ){
        int totalPrice = 0;

        totalPrice += shirts.stream().
                map(ShirtDTO::getPrice).mapToInt(Integer::intValue).sum();

        totalPrice += documents.stream().
                map(DocumentDTO::getPrice).mapToInt(Integer::intValue).sum();

        totalPrice += mugs.stream().
                map(MugDTO::getPrice).mapToInt(Integer::intValue).sum();


        return totalPrice;
    }

    public BufferedImage generateEAN13BarcodeImage(String barcodeText) {
        EAN13Bean barcodeGenerator = new EAN13Bean();
        BitmapCanvasProvider canvas =
                new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        barcodeGenerator.generateBarcode(canvas, barcodeText);
        return canvas.getBufferedImage();
    }

    @GetMapping("barcode/download/{joId}")
    public ResponseEntity<?> getImageByName(@PathVariable("joId") String name){
        byte[] image = orderService.getImage(name);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\""+ ".png")
                .body(image);
    }

    @GetMapping("document/download/{id}")
    public ResponseEntity<?> getDocumentFileByName(@PathVariable("id") Integer id) throws IOException {
        DocumentDTO docDTO = documentService.getDocumentDTOById(id);
        byte[] content = docDTO.getFile().getBytes();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docDTO.getFileName() + "\""+ ".pdf")
                .body(content);
    }

    @GetMapping("shirt/download/{id}")
    public ResponseEntity<?> getShirtFileByName(@PathVariable("id") Integer id) throws IOException {
        ShirtDTO dto = shirtService.getShirtDTOById(id);
        byte[] content = dto.getFile().getBytes();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.getFileName() + "\""+ ".pdf")
                .body(content);
    }

    @GetMapping("mug/download/{id}")
    public ResponseEntity<?> getMugFileByName(@PathVariable("id") Integer id) throws IOException {
        MugDTO dto = mugService.getMugDTOById(id);
        byte[] content = dto.getFile().getBytes();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.getFileName() + "\""+ ".pdf")
                .body(content);
    }


    @GetMapping("/logout")
    public String logout(Model model){
        loggedUser = null;
        loggedCustomer = null;
        mugOrders = null;
        shirtOrders = null;
        documentOrders = null;
        orders = null;
        this.model = null;
        model.addAttribute("logoutMessage","User has successfully logout" );
        return loginPage(model);
    }

    @GetMapping("adminUploadShirtDesign")
    public String adminUploadShirtDesign(Model model){
        model.addAttribute("designs", getAllShirtDesigns());
        return "admin_upload_shirt_design";
    }

    @GetMapping("adminUploadMugDesign")
    public String adminUploadMugDesign(Model model){
        model.addAttribute("designs", getAllMugDesigns());
        return "admin_upload_mug_design";
    }

    List<ShirtDesignDTO> getAllShirtDesigns(){
        List<ShirtDesignDTO> shirtDesigns = shirtDesignService.getAllDesign().
                stream().map(ShirtDesignDTO::new).toList();

        shirtDesigns.forEach(design -> {

            String customizedDesignName = design.getName();
            File file = new File(SHIRT_DESIGN_PATH + customizedDesignName);
            Path downloadPath = Paths.get(SHIRT_DESIGN_PATH);
            String mimeType = null;
            try {
                mimeType = Files.probeContentType(downloadPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Path filePath = downloadPath.resolve(customizedDesignName);
            DiskFileItem fileItem = new DiskFileItem(customizedDesignName, mimeType, false, file.getName(), (int) file.length(), file.getParentFile());
            fileItem.getOutputStream();
            CustomMultipartFile customMultipartFile = new CustomMultipartFile(filePath);
            String path = SHIRT_DESIGN_PATH+ design.getName().replaceAll(" ", "%20");
            design.setPath("http://localhost:8081/" + design.getName());
            design.setName(design.getName());

        });
        return shirtDesigns;
    }


    List<MugDesignDTO> getAllMugDesigns(){
        List<MugDesignDTO> shirtDesigns = mugDesignService.getAllMugDesigns().
                stream().map(MugDesignDTO::new).toList();

        shirtDesigns.forEach(design -> {

            String customizedDesignName = design.getName();
            File file = new File(MUG_DESIGN_PATH + customizedDesignName);
            Path downloadPath = Paths.get(MUG_DESIGN_PATH);
            String mimeType = null;
            try {
                mimeType = Files.probeContentType(downloadPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Path filePath = downloadPath.resolve(customizedDesignName);
            DiskFileItem fileItem = new DiskFileItem(customizedDesignName, mimeType, false, file.getName(), (int) file.length(), file.getParentFile());
            fileItem.getOutputStream();
            CustomMultipartFile customMultipartFile = new CustomMultipartFile(filePath);
            String path = MUG_DESIGN_PATH+ design.getName().replaceAll(" ", "%20");
            design.setPath("http://localhost:8081/" + design.getName());
            design.setName(design.getName());

        });
        return shirtDesigns;
    }

    @PostMapping("addShirtDesign")
    public String addShirtDesign(List<MultipartFile> files, Model model) throws IOException {

        files.forEach(file -> {
            try{
                ShirtDesignDTO shirtDesignDTO = new ShirtDesignDTO();
                String uploadDir = SHIRT_DESIGN_PATH
                        + file.getOriginalFilename();
                String contentType = StringUtils.getFilenameExtension(file.getOriginalFilename());
                File tempFile = new File(uploadDir);
                CustomMultipartFile customMultipartFile = new CustomMultipartFile(tempFile.toPath());
                file.transferTo(tempFile);
                //Set contents of new Design
                shirtDesignDTO.setName(tempFile.getName());
                shirtDesignDTO.setType(contentType);

                //Save Design
                shirtDesignService.saveDesign(new ShirtDesign(shirtDesignDTO));
            }
            catch(IOException e){
                System.out.println( "ERROR: "+e.getMessage());
            }
        });

        return adminUploadShirtDesign(model);
    }

    @PostMapping("addMugDesign")
    public String addMugDesign(List<MultipartFile> files, Model model) throws IOException {

        files.forEach(file -> {
            try{
                MugDesignDTO mugDesignDTO = new MugDesignDTO();
                String uploadDir = MUG_DESIGN_PATH
                        + file.getOriginalFilename();
                String contentType = StringUtils.getFilenameExtension(file.getOriginalFilename());
                File tempFile = new File(uploadDir);
                file.transferTo(tempFile);
                //Set contents of new Design
                mugDesignDTO.setName(tempFile.getName());
                mugDesignDTO.setType(contentType);

                //Save Design
                mugDesignService.saveDesign(new MugDesign(mugDesignDTO));
            }
            catch(IOException e){
                System.out.println( "ERROR: "+e.getMessage());
            }
        });

        return adminUploadMugDesign(model);
    }

    @GetMapping("/design/shirt/delete/{id}")
    public String deleteShirtDesign(@PathVariable("id") Integer id, Model model) throws IOException {
        shirtDesignService.deleteDesignById(id);

        return adminUploadShirtDesign(model);
    }


    @GetMapping("/design/mug/delete/{id}")
    public String deleteMugDesign(@PathVariable("id") Integer id, Model model) throws IOException {
        mugDesignService.deleteDesignById(id);

        return adminUploadShirtDesign(model);
    }

    @PostMapping("export-csv")
    public void exportCSV(CsvDTO csvDTO, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"orders" + ".csv\"");
        List<Order> retrievedOrders = orderService.
                getAllOrdersByDate(csvDTO.getDateFrom(), csvDTO.getDateTo());
        csvService.printOrders(retrievedOrders,response.getWriter());
    }
}
