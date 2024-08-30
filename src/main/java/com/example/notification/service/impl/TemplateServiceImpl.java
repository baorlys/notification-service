package com.example.notification.service.impl;

import com.example.notification.global.factory.ContentFactory;
import com.example.notification.global.service.CommonService;
import com.example.notification.input.TemplateInput;
import com.example.notification.model.TemplateMessage;
import com.example.notification.model.User;
import com.example.notification.processor.TemplateProcessor;
import com.example.notification.repository.TemplateRepository;
import com.example.notification.repository.UserRepository;
import com.example.notification.service.TemplateService;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    UserRepository userRepository;
    TemplateRepository templateRepository;
    TemplateProcessor templateProcessor;
    ContentFactory contentFactory;

    @Override
    public TemplateMessage createTemplate(UUID userId, TemplateInput input) {
        User user = userRepository.findById(userId).orElse(null);
        try {
            String content = contentFactory.extractContent(input);
            TemplateMessage templateMessage = new TemplateMessage(UUID.randomUUID(), user, input.getName(), input.getType(), content);
            return templateRepository.save(templateMessage);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }


    @Override
    public String processTemplate(Map<String, Object> data, TemplateMessage templateMsg) {
        try {
            return templateProcessor.processTemplate(data, templateMsg);
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public TemplateMessage getTemplateById(UUID id) {
        return templateRepository.findById(id).orElse(null);
    }

    @Override
    public List<TemplateMessage> getUserTemplates(UUID userId, int pageNum, int pageSize, String sortBy) {
        Sort sort = (sortBy != null && !sortBy.isEmpty()) ? Sort.by(sortBy) : Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return templateRepository.findTemplatesByOwner_Id(userId, pageable);
    }

    @Override
    public TemplateMessage updateTemplate(UUID id, TemplateInput input) {
        TemplateMessage t = getTemplateById(id);
        CommonService.throwIsNull(t, "Not found template");
        t.setName(input.getName());
        t.setBody(input.getContent());
        return templateRepository.save(t);
    }

}
