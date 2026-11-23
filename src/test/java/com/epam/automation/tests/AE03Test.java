package com.epam.automation.tests;

import com.epam.automation.pages.ContactUsPage;
import com.epam.automation.support.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class AE03Test extends BaseTest {
    @Test
    public void submitContactFormWithAttachment() {
        // 1 Navigate home
        home.openHome(); Assert.assertTrue(home.isDisplayed(), "Step 1: Home page");
        // 2 Open Contact Us
        ContactUsPage contact = home.openContactUs(); Assert.assertTrue(contact.isFormDisplayed(), "Step 2: Contact form");
        // 3 Name
        contact.enterName(TestData.CONTACT_NAME); Assert.assertEquals(contact.getName(), TestData.CONTACT_NAME, "Step 3: Name");
        // 4 Email
        contact.enterEmail(TestData.CONTACT_EMAIL); Assert.assertEquals(contact.getEmail(), TestData.CONTACT_EMAIL, "Step 4: Email");
        // 5 Subject
        contact.enterSubject(TestData.CONTACT_SUBJECT); Assert.assertEquals(contact.getSubject(), TestData.CONTACT_SUBJECT, "Step 5: Subject");
        // 6 Message
        contact.enterMessage(TestData.CONTACT_MESSAGE); Assert.assertEquals(contact.getMessage(), TestData.CONTACT_MESSAGE, "Step 6: Message");
        // 7 Attachment
        contact.attachFile(TestData.attachment()); Assert.assertEquals(contact.getSelectedFileName(), "epam-test-attachment.txt", "Step 7: Attachment");
        // 8 Submit and inspect native confirmation
        contact.submitForm(); Assert.assertTrue(contact.isConfirmationAlertPresent(), "Step 8: Native confirm present");
        Assert.assertEquals(contact.getConfirmationText(), TestData.CONFIRM_TEXT, "Step 8: Confirm text");
        // 9 Accept confirmation
        contact.acceptConfirmation(); Assert.assertFalse(contact.isConfirmationAlertPresent(), "Step 9: Confirm dismissed");
        // 10 Verify success
        Assert.assertTrue(contact.isSuccessMessageDisplayed(), "Step 10: Success visible");
        Assert.assertEquals(contact.getSuccessMessage(), TestData.SUCCESS_TEXT, "Step 10: Success text");
    }
}
