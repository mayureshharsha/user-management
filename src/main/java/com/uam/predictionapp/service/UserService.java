package com.uam.predictionapp.service;

import com.uam.predictionapp.model.entity.ResultEntity;
import com.uam.predictionapp.model.entity.UserEntity;
import com.uam.predictionapp.repository.ResultRepository;
import com.uam.predictionapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.uam.predictionapp.contants.AppConstants.INITIAL_POINTS;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResultRepository resultRepository;

    /**
     * @param userEntity
     */
    public void create(UserEntity userEntity) {
        userRepository.save(userEntity);
        createResultForUser(userEntity.getUsername());
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
}
