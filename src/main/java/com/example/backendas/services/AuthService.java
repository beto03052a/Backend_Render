package com.example.backendas.services;

import com.example.backendas.dtos.auth.AuthenticationRequest;
import com.example.backendas.dtos.auth.AuthenticationResponse;
import com.example.backendas.dtos.auth.RegisterRequest;

public interface AuthService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}

