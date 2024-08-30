package com.example.notification;

import com.example.notification.enums.TemplateType;
import com.example.notification.input.TemplateInput;
import com.example.notification.model.TemplateMessage;
import com.example.notification.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TemplateMessageServiceTest {
    @Autowired
    private TemplateService templateService;

    @Test
    void testCreateHtmlTemplate() throws IOException {
        String tempName = "HTML Test";
        String path = "test.html";
        UUID userId = UUID.fromString("4C6B2B82-3807-44D4-B7BF-9C5A11B0D42F");

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: " + path);
        }
        byte[] fileContent = inputStream.readAllBytes();
        MockMultipartFile mockFile = new MockMultipartFile("file", path, "text/html", fileContent);
        TemplateInput input = new TemplateInput();
        input.setName(tempName);
        input.setType(TemplateType.HTML);
        input.setFile(mockFile);
        TemplateMessage templateMessage = templateService.createTemplate(userId, input);
        assertNotNull(templateMessage);
        assertEquals(TemplateType.HTML, templateMessage.getTemplateType());
    }

    @Test
    void testCombineTemplate() {
        String name = "Khoa";
        int age = 21;
        String job = "Software Engineering";
        TemplateMessage temp = templateService.getTemplateById(UUID.fromString("34636DDF-B384-4BA3-8546-E49903202D00"));
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("age", age);
        model.put("job", job);
        String result = templateService.processTemplate(model, temp);
        assertNotEquals("", result);
    }

    @Test
    void testGetTemplateById() {
        UUID id = UUID.fromString("692ADD3A-1CCC-496C-B4A3-2A95FCDFE57C");
        TemplateMessage t = templateService.getTemplateById(id);
        assertNotNull(t);
    }

    @Test
    void testGetListTemplate() {
        UUID userId = UUID.fromString("4C6B2B82-3807-44D4-B7BF-9C5A11B0D42F");
        int pageSize = 10;
        int pageNum = 0;
        String sortBy = "name";
        List<TemplateMessage> templateMessages = templateService.getUserTemplates(userId, pageNum, pageSize, sortBy);
        assertTrue(templateMessages.size() > 0);
    }

    @Test
    void testUpdateTemplate() {
        UUID id = UUID.fromString("47670D2D-E48E-4AC9-BDB7-10A6AF399486");
        TemplateInput input = new TemplateInput();
        input.setName("new name");
        input.setContent("new string ${age}");
        TemplateMessage t = templateService.updateTemplate(id, input);
        assertNotNull(t);
    }
}
