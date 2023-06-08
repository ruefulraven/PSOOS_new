package com.main.psoos.controller;

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
import com.main.psoos.model.Document;
import com.main.psoos.model.MugDesign;
import com.main.psoos.model.Order;
import com.main.psoos.model.ShirtDesign;
import com.main.psoos.model.User;
import com.main.psoos.service.CustomMultipartFile;
import com.main.psoos.service.CustomerService;
import com.main.psoos.service.DocumentService;
import com.main.psoos.service.MugDesignService;
import com.main.psoos.service.MugService;
import com.main.psoos.service.OrderService;
import com.main.psoos.service.ShirtDesignService;
import com.main.psoos.service.ShirtService;
import com.main.psoos.service.UserService;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
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

    List<DocumentDTO> documentOrders = new ArrayList<>();
    List<MugDTO> mugOrders = new ArrayList<>();
    List<ShirtDTO> shirtOrders = new ArrayList<>();
    List<OrderDTO> orders = new ArrayList<>();
    private Customer loggedCustomer = new Customer();
    private User loggedUser = new User();
    private Map<String, Object> model = new HashMap<>();

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

        return "admin";
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
                    filter(order -> order.getStatus().equals("PENDING") || order.getStatus().equals("ACCEPTED")).
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
            case "USER_CLIENT" :
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

    @PostMapping("/updateAccount")
    public String updateAccount(CustomerDTO customerDTO, Model model){
        Customer tempCustomer = customerService.getCustomerByName(customerDTO.getCustomerName());
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

        return "account";
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
        return "myOrders";
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
    public String uploadMugPage(){
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
    public String uploadShirtPage(){
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
        System.out.println(totalPrice + "HOHO");
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
        System.out.println(totalPrice + "HOHO");
        model.addAttribute("totalPrice", totalPrice);
        return "cartOrders";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(Model model)throws IOException {
        OrderDTO orderDTO = new OrderDTO();
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
            dto.setData(customMultipartFile);

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
            dto.setData(customMultipartFile);

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

            String uploadDir = "C:\\Users\\Axel\\Downloads\\" + loggedUser.getUserId();
            String fileName = StringUtils.cleanPath(tempShirt.getFile().getOriginalFilename());
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectory(uploadPath);
            }
            try{
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

    public void logout(){
        loggedUser = null;
        loggedCustomer = null;
        mugOrders = null;
        shirtOrders = null;
        documentOrders = null;
    }
}
