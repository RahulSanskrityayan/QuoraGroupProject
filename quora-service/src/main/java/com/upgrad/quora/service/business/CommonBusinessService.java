package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonBusinessService {

    @Autowired
    private UserDao userDao;

    //Method to get userdetails based on Uuid
    public UserEntity getUser(String Uuid) throws UserNotFoundException {
        UserEntity userDetails = userDao.getUserDetails(Uuid);
        if (userDetails == null) {
            throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");
        }
        return userDetails;
    }

    //Method to get user based on AccessToken
    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity validateUser(String token) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthEntity = userDao.validateUser(token);
        if (userAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        return userAuthEntity;
    }

    //Method to get details of the signed in user
    public UserAuthTokenEntity getCurrentUserDeatils(String userId, String token) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthDetails = userDao.getUserAuthDetails(userId, token);
        if (userAuthDetails == null) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get user details");
        }
        return userAuthDetails;
    }
}
