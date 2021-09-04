//package com.houndsoft.towerbridge.services.controller;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import javax.validation.Valid;
//
//import com.houndsoft.towerbridge.services.model.Usuario;
//import com.houndsoft.towerbridge.services.model.Usuario.Perfil;
//import com.houndsoft.towerbridge.services.repository.UsuarioRepository;
//import com.houndsoft.towerbridge.services.request.LoginRequest;
//import com.houndsoft.towerbridge.services.request.SignupRequest;
//import com.houndsoft.towerbridge.services.response.JwtResponse;
//import com.houndsoft.towerbridge.services.response.MessageResponse;
//import com.houndsoft.towerbridge.services.security.jwt.JwtUtils;
//import com.houndsoft.towerbridge.services.security.services.UserDetailsImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotatsion.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
////@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    UsuarioRepository usuarioRepository;
//
//    @Autowired
//    PasswordEncoder encoder;
//
//    @Autowired
//    JwtUtils jwtUtils;
//
//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtils.generateJwtToken(authentication);
//
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        Perfil perfil = userDetails.getAuthorities().stream()
//                .map(item -> Perfil.valueOf(item.getAuthority()))
//                .collect(Collectors.toList()).get(0);
//
//        return ResponseEntity.ok(new JwtResponse(jwt,
//                userDetails.getId(),
//                userDetails.getUsername(),
//                userDetails.getEmail(),
//                perfil));
//    }
//}
