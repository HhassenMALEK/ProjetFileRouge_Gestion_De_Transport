package com.api.ouimouve.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PhotoURLValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void isValid_ShouldValidateCorrectly() {
        // Create the validator instance
        PhotoURLValidator validator = new PhotoURLValidator();

        // Test valid URLs
        assertTrue(validator.isValid("http://example.com/image.jpg", context), "Should accept HTTP URL with JPG extension");
        assertTrue(validator.isValid("https://example.com/image.png", context), "Should accept HTTPS URL with PNG extension");
        assertTrue(validator.isValid("https://example.com/image.svg", context), "Should accept HTTPS URL with SVG extension");

        // Test invalid URLs
        assertFalse(validator.isValid(null, context), "Should reject null URL");
        assertFalse(validator.isValid("ftp://example.com/image.jpg", context), "Should reject non-HTTP/HTTPS protocol");
        assertFalse(validator.isValid("http://example.com/image.gif", context), "Should reject unsupported image extension");
        assertFalse(validator.isValid("http://example.com/image", context), "Should reject URL without extension");
    }
}