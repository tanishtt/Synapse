package com.tanishmohanta.synapse.user_service.service;

import com.tanishmohanta.synapse.user_service.dto.SignupRequestDTO;
import com.tanishmohanta.synapse.user_service.dto.UserDTO;
import com.tanishmohanta.synapse.user_service.entity.UserEntity;
import com.tanishmohanta.synapse.user_service.exception.BadRequestException;
import com.tanishmohanta.synapse.user_service.repository.UserRepository;
import com.tanishmohanta.synapse.user_service.utils.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDTO signUp(SignupRequestDTO signupRequestDTO) {
        log.info("Signing up user with email: {}", signupRequestDTO.getEmail());

        boolean exists = userRepository.existsByEmail(signupRequestDTO.getEmail());
        if (exists) {
            throw new BadRequestException("Email already exists: " + signupRequestDTO.getEmail());
        }
        UserEntity userEntity = modelMapper.map(signupRequestDTO, UserEntity.class);
        userEntity.setPassword(Bcrypt.hashPassword(signupRequestDTO.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public String login(SignupRequestDTO loginRequestDTO) {
        log.info("Login user with email: {}", loginRequestDTO.getEmail());

        UserEntity userEntity = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        boolean passwordMatches = Bcrypt.matchPassword(loginRequestDTO.getPassword(), userEntity.getPassword());
        if (!passwordMatches) {
            throw new BadRequestException("Invalid email or password");
        }
        return jwtService.generateAccessToken(userEntity);
    }
}
