package com.example.notification.service.impl;

import com.example.notification.enums.TemplateType;
import com.example.notification.global.factory.ContentFactory;
import com.example.notification.global.service.CommonService;
import com.example.notification.input.TemplateInput;
import com.example.notification.model.Template;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    public Template createTemplate(UUID userId, TemplateInput input) {
        User user = userRepository.findById(userId).orElse(null);
        try {
            String content = contentFactory.extractContent(input);
            Template template = new Template(UUID.randomUUID(), user, input.getName(), input.getType(), content);
            return templateRepository.save(template);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }


    @Override
    public String processTemplate(Map<String, Object> data, String template) {
        try {
            return templateProcessor.processTemplate(data, template);
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Template getTemplateById(UUID id) {
        return templateRepository.findById(id).orElse(null);
    }

    @Override
    public List<Template> getUserTemplates(UUID userId, int pageNum, int pageSize, String sortBy) {
        Sort sort = (sortBy != null && !sortBy.isEmpty()) ? Sort.by(sortBy) : Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return templateRepository.findTemplatesByOwner_Id(userId, pageable);
    }

    @Override
    public Template updateTemplate(UUID id, TemplateInput input) {
        Template t = getTemplateById(id);
        CommonService.throwIsNull(t, "Not found template");
        t.setName(input.getName());
        t.setBody(input.getContent());
        return templateRepository.save(t);
    }

}
