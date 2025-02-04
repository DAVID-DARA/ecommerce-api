package com.project.ecommerce_api.services;

import com.project.ecommerce_api.utilities.EmailType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    private static final String welcomeEmailBody = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title></title>

                <!--[if !mso]><!-->
                <style type="text/css">
            @import url('https://fonts.mailersend.com/css?family=Inter:400,600');
                </style>
                <!--<![endif]-->

                <style type="text/css" rel="stylesheet" media="all">
            @media only screen and (max-width: 640px) {

                    .ms-header {
                        display: none !important;
                    }
                    .ms-content {
                        width: 100% !important;
                        border-radius: 0;
                    }
                    .ms-content-body {
                        padding: 30px !important;
                    }
                    .ms-footer {
                        width: 100% !important;
                    }
                    .mobile-wide {
                        width: 100% !important;
                    }
                    .info-lg {
                        padding: 30px;
                    }
                }
                </style>
                <!--[if mso]>
                    <style type="text/css">
                    body { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    td { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    td * { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    td p { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    td a { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    td span { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    td div { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    td ul li { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    td ol li { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    td blockquote { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    th * { font-family: Arial, Helvetica, sans-serif!important  !important; }
                    </style>
                    <![endif]-->
            </head>
            <body style="font-family:'Inter', Helvetica, Arial, sans-serif; width: 100% !important; height: 100%; margin: 0; padding: 0; -webkit-text-size-adjust: none; background-color: #f4f7fa; color: #4a5566;">

                <div class="preheader" style="display:none !important;visibility:hidden;mso-hide:all;font-size:1px;line-height:1px;max-height:0;max-width:0;opacity:0;overflow:hidden;"></div>

                <table class="ms-body" width="100%" cellpadding="0" cellspacing="0" role="presentation" style="border-collapse:collapse;background-color:#f4f7fa;width:100%;margin-top:0;margin-bottom:0;margin-right:0;margin-left:0;padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;">
                    <tr>
                        <td align="center" style="word-break:break-word;font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;">

                            <table class="ms-container" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;width:100%;margin-top:0;margin-bottom:0;margin-right:0;margin-left:0;padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;">
                                <tr>
                                    <td align="center" style="word-break:break-word;font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;">

                                        <table class="ms-header" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
                                            <tr>
                                                <td height="40" style="font-size:0px;line-height:0px;word-break:break-word;font-family:'Inter', Helvetica, Arial, sans-serif;">
                                                    &nbsp;
                                                </td>
                                            </tr>
                                        </table>

                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" style="word-break:break-word;font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;">

                                        <table class="ms-content" width="640" cellpadding="0" cellspacing="0" role="presentation" style="border-collapse:collapse;width:640px;margin-top:0;margin-bottom:0;margin-right:auto;margin-left:auto;padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;background-color:#FFFFFF;border-radius:6px;box-shadow:0 3px 6px 0 rgba(0,0,0,.05);">
                                            <tr>
                                                <td class="ms-content-body" style="word-break:break-word;font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;padding-top:40px;padding-bottom:40px;padding-right:50px;padding-left:50px;">

                                                    <p class="logo" style="margin-right:0;margin-left:0;line-height:28px;font-weight:600;font-size:21px;color:#111111;text-align:center;margin-top:0;margin-bottom:40px;">
                                                        <span style="color:#0052e2;font-family:Arial, Helvetica, sans-serif;font-size:30px;vertical-align:bottom;">❖&nbsp;</span>LOREM
                                                    </p>

                                                    <h1 style="margin-top:0;color:#111111;font-size:24px;line-height:36px;font-weight:600;margin-bottom:24px;">Welcome, {$name}!</h1>
                                                    <p style="color:#4a5566;margin-top:20px;margin-bottom:20px;margin-right:0;margin-left:0;font-size:16px;line-height:28px;">
                                                        Thanks for trying LOREM Clothing Store. We’re thrilled to have you on board. To get the most out of LOREM, do this next step:
                                                    </p>


                                                    <p style="color:#4a5566;margin-top:20px;margin-bottom:20px;margin-right:0;margin-left:0;font-size:16px;line-height:28px;">
                                                        For reference, here's your otp for verification:
                                                    </p>

                                                    <table width="100%" cellpadding="0" cellspacing="0" role="presentation" style="border-collapse:collapse;">
                                                        <tr>
                                                            <td class="info" style="word-break:break-word;font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;padding-top:20px;padding-bottom:20px;padding-right:20px;padding-left:20px;border-radius:4px;background-color:#f4f7fa;">

                                                                <table width="100%" cellpadding="0" cellspacing="0" role="presentation" style="border-collapse:collapse;">

                                                                    <tr>
                                                                        <td style="word-break:break-word;font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;">
                                                                            <strong style="font-weight:600;">OTP:</strong> {$otp}
                                                                        </td>
                                                                    </tr>
                                                                </table>

                                                            </td>
                                                        </tr>
                                                    </table>

                                                    <p style="color:#4a5566;margin-top:20px;margin-bottom:20px;margin-right:0;margin-left:0;font-size:16px;line-height:28px;">
                                                        Cheers,
                                                        <br>Dara and the LOREM Team
                                                    </p>

                                                    <p style="color:#4a5566;margin-top:20px;margin-bottom:20px;margin-right:0;margin-left:0;font-size:16px;line-height:28px;">
                                                        <strong style="font-weight:600;">P.S.</strong> Need help getting started? Check out our <a href="{$help_url}" style="color:#0052e2;">help documentation</a>.
                                                    </p>

                                                    <table width="100%" style="border-collapse:collapse;">
                                                      \s
                                                        <tr>
                                                            <td height="20" style="font-size:0px;line-height:0px;border-top-width:1px;border-top-style:solid;border-top-color:#e2e8f0;word-break:break-word;font-family:'Inter', Helvetica, Arial, sans-serif;">
                                                                &nbsp;
                                                            </td>
                                                        </tr>
                                                    </table>

                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" style="word-break:break-word;font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;">

                                                    <table class="ms-footer" width="640" cellpadding="0" cellspacing="0" role="presentation" style="border-collapse:collapse;width:640px;margin-top:0;margin-bottom:0;margin-right:auto;margin-left:auto;">
                                                        <tr>
                                                            <td class="ms-content-body" align="center" style="word-break:break-word;font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;padding-top:40px;padding-bottom:40px;padding-right:50px;padding-left:50px;">
                                                                <p class="small" style="margin-top:20px;margin-bottom:20px;margin-right:0;margin-left:0;color:#96a2b3;font-size:14px;line-height:21px;">
                                                                    &copy; 2024 LOREM. All rights reserved.
                                                                </p>
                                                                <p class="small" style="margin-top:20px;margin-bottom:20px;margin-right:0;margin-left:0;color:#96a2b3;font-size:14px;line-height:21px;">
                                                                    1234 Street Rd.
                                                                    <br>Suite 1234
                                                                    <br>City, State, ZIP Code
                                                                </p>
                                                            </td>
                                                        </tr>
                                                    </table>

                                                </td>
                                            </tr>
                                        </table>

                                    </td>
                                </tr>
                            </table>

                        </td>
                    </tr>
                </table>

            </body>
            </html>""";

    private static final String resendOtpEmailBody = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title></title>
                <!--[if !mso]><!-->
                <style type="text/css">
                    @import url('https://fonts.mailersend.com/css?family=Inter:400,600');
                </style>
                <!--<![endif]-->
                <style type="text/css" rel="stylesheet" media="all">
                    @media only screen and (max-width: 640px) {
                        .ms-header { display: none !important; }
                        .ms-content { width: 100% !important; border-radius: 0; }
                        .ms-content-body { padding: 30px !important; }
                        .ms-footer { width: 100% !important; }
                    }
                </style>
            </head>
            <body style="font-family:'Inter', Helvetica, Arial, sans-serif; width: 100% !important; height: 100%; margin: 0; padding: 0; -webkit-text-size-adjust: none; background-color: #f4f7fa; color: #4a5566;">
                <div class="preheader" style="display:none !important;visibility:hidden;mso-hide:all;font-size:1px;line-height:1px;max-height:0;max-width:0;opacity:0;overflow:hidden;"></div>
                <table class="ms-body" width="100%" cellpadding="0" cellspacing="0" role="presentation" style="background-color:#f4f7fa;width:100%;margin:0;padding:0;">
                    <tr>
                        <td align="center" style="font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;">
                            <table class="ms-container" width="100%" cellpadding="0" cellspacing="0" style="width:100%;margin:0;padding:0;">
                                <tr>
                                    <td align="center" style="font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;">
                                        <table class="ms-content" width="640" cellpadding="0" cellspacing="0" role="presentation" style="background-color:#FFFFFF;border-radius:6px;box-shadow:0 3px 6px 0 rgba(0,0,0,.05);margin:auto;padding:0;">
                                            <tr>
                                                <td class="ms-content-body" style="font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;padding:40px 50px;">
                                                    <p class="logo" style="text-align:center;font-weight:600;font-size:21px;color:#111111;margin-bottom:40px;">
                                                        <span style="color:#0052e2;font-family:Arial, Helvetica, sans-serif;font-size:30px;vertical-align:bottom;">❖&nbsp;</span>LOREM
                                                    </p>
                                                    <h1 style="color:#111111;font-size:24px;line-height:36px;font-weight:600;margin-bottom:24px;">Hello, {$name}!</h1>
                                                    <p style="color:#4a5566;font-size:16px;line-height:28px;margin:20px 0;">
                                                        We noticed you requested to resend your OTP. Here is your new OTP for verification:
                                                    </p>
                                                    <table width="100%" cellpadding="0" cellspacing="0" role="presentation" style="border-collapse:collapse;">
                                                        <tr>
                                                            <td class="info" style="background-color:#f4f7fa;border-radius:4px;padding:20px;">
                                                                <strong style="font-weight:600;">OTP:</strong> {$otp}
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <p style="color:#4a5566;font-size:16px;line-height:28px;margin:20px 0;">
                                                        Please use this OTP within the next 10 minutes to complete your verification process.
                                                    </p>
                                                    <p style="color:#4a5566;font-size:16px;line-height:28px;margin:20px 0;">
                                                        Cheers,<br>Dara and the LOREM Team
                                                    </p>
                                                    <p style="color:#4a5566;font-size:16px;line-height:28px;margin:20px 0;">
                                                        <strong style="font-weight:600;">P.S.</strong> Need help? Visit our <a href="{$help_url}" style="color:#0052e2;">support page</a>.
                                                    </p>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" style="font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;">
                                                    <table class="ms-footer" width="640" cellpadding="0" cellspacing="0" role="presentation" style="width:640px;margin:auto;padding:40px 50px;">
                                                        <tr>
                                                            <td class="ms-content-body" align="center" style="font-family:'Inter', Helvetica, Arial, sans-serif;font-size:16px;line-height:24px;">
                                                                <p class="small" style="color:#96a2b3;font-size:14px;line-height:21px;">
                                                                    &copy; 2024 LOREM. All rights reserved.
                                                                </p>
                                                                <p class="small" style="color:#96a2b3;font-size:14px;line-height:21px;">
                                                                    1234 Street Rd.<br>Suite 1234<br>City, State, ZIP Code
                                                                </p>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """;

    public void sendEmail(String to, String userName, String token, EmailType emailType) throws MessagingException {
        String finalMailContent = "";
        String subject = "";

        finalMailContent += resendOtpEmailBody.replace("{$name}", userName).replace("{$otp}", token).replace("{$help_url}", "https://example.com/support");

        if (emailType == EmailType.EMAIL_VERIFICATION)
            subject += "Verify Email";
        else if (emailType == EmailType.PASSWORD_RESET)
            subject += "Request for OTP";

        jakarta.mail.internet.MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom("no-reply@lorem.com");
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(finalMailContent, "text/html; charset=utf-8");

        javaMailSender.send(message);
    }

    public void sendWelcomeEmail(String to, String userName, String token) throws MessagingException {
        String finalMailContent = "";
        String subject = "";

        subject += "Welcome to Our Service";
        finalMailContent += welcomeEmailBody.replace("{$name}", userName).replace("{$otp}", token).replace("{$help_url}", "https://example.com/support");

        jakarta.mail.internet.MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom("no-reply@lorem.com");
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(finalMailContent, "text/html; charset=utf-8");

        javaMailSender.send(message);
    }

}
