package com.example.notification;

import com.example.notification.enums.TemplateType;
import com.example.notification.input.TemplateInput;
import com.example.notification.model.Template;
import com.example.notification.service.TemplateService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
class TemplateServiceTest {
    @Autowired
    private TemplateService templateService;

    @Test
    void testCreateHtmlTemplate() {
        String tempName = "test template";
        String html = "<html><body>" +
                "    <h1>Profile Information</h1>" +
                "    <p><strong>Name:</strong>${name}</p>" +
                "    <p><strong>Age:</strong>${age}</p>" +
                "    <p><strong>Job:</strong>${job}</p>" +
                "</body>" +
                "</html>";
        UUID userId = UUID.fromString("4C6B2B82-3807-44D4-B7BF-9C5A11B0D42F");
        Template template = templateService.createTemplate(userId, tempName, TemplateType.HTML, html);
        assertNotNull(template);
        assertEquals(TemplateType.HTML, template.getTemplateType());
    }

    @Test
    void testCombineTemplate() {
        String name = "Khoa";
        int age = 21;
        String job = "Software Engineering";
        Template temp = templateService.getTemplateById(UUID.fromString("47670D2D-E48E-4AC9-BDB7-10A6AF399486"));
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("age", age);
        model.put("job", job);
        String result = templateService.processTemplate(model, temp.getBody());
        assertNotEquals("", result);
    }

    @Test
    void testGetTemplateById() {
        UUID id = UUID.fromString("47670D2D-E48E-4AC9-BDB7-10A6AF399486");
        Template t = templateService.getTemplateById(id);
        assertNotNull(t);
    }

    @Test
    void testGetListTemplate() {
        UUID userId = UUID.fromString("4C6B2B82-3807-44D4-B7BF-9C5A11B0D42F");
        int pageSize = 10;
        int pageNum = 0;
        String sortBy = "name";
        List<Template> templates = templateService.getUserTemplates(userId, pageNum, pageSize, sortBy);
        assertTrue(templates.size() > 0);
    }

    @Test
    void testUpdateTemplate() {
        UUID id = UUID.fromString("47670D2D-E48E-4AC9-BDB7-10A6AF399486");
        TemplateInput input = new TemplateInput();
        input.setName("new name");
        input.setContent("new string ${age}");
        Template t = templateService.updateTemplate(id, input);
        assertNotNull(t);
    }
}
