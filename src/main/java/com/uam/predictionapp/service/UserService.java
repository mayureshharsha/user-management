package com.uam.predictionapp.service;

import com.uam.predictionapp.model.TokenDto;
import com.uam.predictionapp.model.dto.UserDto;
import com.uam.predictionapp.model.entity.ResultEntity;
import com.uam.predictionapp.model.entity.UserEntity;
import com.uam.predictionapp.repository.ResultRepository;
import com.uam.predictionapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.uam.predictionapp.contants.AppConstants.INITIAL_POINTS;
import static com.uam.predictionapp.contants.AppConstants.TOKEN_HASH_MAP;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResultRepository resultRepository;

    /**
     * @param userEntity
     * @return
     */
    public boolean create(UserEntity userEntity) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(userEntity.getUsername());
        if(byUsername.isPresent()){
            return false;
        }
        userRepository.save(userEntity);
        createResultForUser(userEntity.getUsername());
        return true;
    }

    public void update(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    private void createResultForUser(String username) {
        final Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            ResultEntity resultEntity = ResultEntity.builder().user(userEntity.get()).points(INITIAL_POINTS).build();
            resultRepository.save(resultEntity);
        }
    }

    public UserEntity getUser(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.get();
    }

    public List<UserEntity> getAllUsers() {
        return (List<UserEntity>) userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public TokenDto login(UserDto userDto) {

        final Optional<UserEntity> userEntity = userRepository.findByUsername(userDto.getUsername());
        if(!userEntity.isPresent()){
            return null;
        }
        final UserEntity entity = userEntity.get();
        if(entity.getPassword().equals(userDto.getPassword()) && entity.getUsername().equals(userDto.getUsername())){
            final TokenDto tokenDto = TokenDto.builder().userId(entity.getId()).token(UUID.randomUUID().toString()).build();
            TOKEN_HASH_MAP.put(tokenDto.getUserId(), tokenDto.getToken());
            return tokenDto;
        }
        return null;
    }
}
