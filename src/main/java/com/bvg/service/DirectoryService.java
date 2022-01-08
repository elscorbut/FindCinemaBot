package com.bvg.service;

import com.bvg.commons.shared.exception.ValidationException;
import com.bvg.domain.DirectoryValue;
import com.bvg.repository.DirectoryRepository;
import com.bvg.repository.IDirectoryValueRepository;
import com.bvg.type.EDirectory;
import com.bvg.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class DirectoryService {

    @Autowired
    private IDirectoryValueRepository directoryValueRepository;

    @Autowired
    private DirectoryRepository directoryRepository;

    @Transactional
    public DirectoryValue getDirectoryValue(EDirectory directory, String value, boolean searchByValue) {

        if (StringUtils.isEmpty(value)) return null;

        DirectoryValue directoryValue;

        if (searchByValue) {
            directoryValue = directoryValueRepository.findByValueAndDirectory(value, directory.name()).orElse(new DirectoryValue());
        } else {
            directoryValue = directoryValueRepository.findByShortValueAndDirectory(value, directory.name()).orElse(new DirectoryValue());
        }

        // Если справочное значение уже есть, то возвращаем его
        if (directoryValue.getId() != null)
            return directoryValue;

        directoryValue.setValue(value);
        directoryValue.setShortValue(value);
        directoryValue.setKey(TextUtils.convertToKey(value));
        directoryValue.setPriority(0);
        directoryValue.setHidden(false);
        directoryValue.setDirectory(directoryRepository.findByName(directory.name()).orElseThrow(() -> new ValidationException("Directory is not found!")));

        directoryValueRepository.saveAndFlush(directoryValue);

        return directoryValue;
    }
}
