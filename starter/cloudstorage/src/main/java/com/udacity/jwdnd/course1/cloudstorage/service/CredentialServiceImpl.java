package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialServiceImpl extends BaseUserRelatedServiceImpl<Credential> implements CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    @Autowired
    public CredentialServiceImpl(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        super(credentialMapper);
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @Override
    public Credential save(Credential entity) {
        updatePasswordEncryption(entity);
        return super.save(entity);
    }

    @Override
    public Credential update(Credential entity, Long id) throws NotFoundException {
        updatePasswordEncryption(entity);
        return super.update(entity, id);
    }

    private void updatePasswordEncryption(Credential entity){
        String encodedKey = encryptionService.getEncodedKey();
        String encryptedPassword = encryptionService.encryptValue(entity.getPassword(), encodedKey);
        entity.setKey(encodedKey);
        entity.setPassword(encryptedPassword);
    }
}
