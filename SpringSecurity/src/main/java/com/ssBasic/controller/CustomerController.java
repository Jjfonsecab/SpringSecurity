package com.ssBasic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class CustomerController {
    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/index")
    public String index(){
        return "Que mas mi so?";
    }
    @GetMapping("/index2")
    public String index2(){
        return "Comoooo es ñero???, OJO NO SECURE!!";
    }
    @GetMapping("/session")
    public ResponseEntity<?> getDetailsSession(){

        String sessionId = "";
        User userObject = null;

        List<Object> sessions = sessionRegistry.getAllPrincipals();

        for (Object session : sessions) {
            if (session instanceof User) {//Recuperamos el usuario de una lista.
                userObject = (User) session;
            }

        List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false); //Recuperamos los id

            for(SessionInformation sessionInformation : sessionInformations){
                sessionId = sessionInformation.getSessionId();//trae el id del usuario que se acabo de registrar
            }
        }

        Map<String, Object> response = new HashMap<>();//RETORNO D EL AINFO REFERENTE AL INICIO DE SESION DEL USUARIO
        response.put("response", "Hello bby!");
        response.put("sessionId", sessionId);
        response.put("sessionUsser", userObject);

        return ResponseEntity.ok(response);
    }

}
