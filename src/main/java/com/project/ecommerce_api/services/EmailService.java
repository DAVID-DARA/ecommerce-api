package com.project.ecommerce_api.services;

import com.project.ecommerce_api.messaging.RabbitMQProducer;
import com.project.ecommerce_api.models.EmailRequest;
import com.project.ecommerce_api.utilities.EmailType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final RabbitMQProducer rabbitMQProducer;

    private static final String welcomeEmailBody = """
            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                   <html dir="ltr" xmlns="http://www.w3.org/1999/xhtml" xmlns:o="urn:schemas-microsoft-com:office:office" lang="en">
                    <head>
                     <meta charset="UTF-8">
                     <meta content="width=device-width, initial-scale=1" name="viewport">
                     <meta name="x-apple-disable-message-reformatting">
                     <meta http-equiv="X-UA-Compatible" content="IE=edge">
                     <meta content="telephone=no" name="format-detection">
                     <title>Main Verification Email</title><!--[if (mso 16)]>
                       <style type="text/css">
                       a {text-decoration: none;}
                       </style>
                       <![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>
                   <noscript>
                            <xml>
                              <o:OfficeDocumentSettings>
                              <o:AllowPNG></o:AllowPNG>
                              <o:PixelsPerInch>96</o:PixelsPerInch>
                              </o:OfficeDocumentSettings>
                            </xml>
                         </noscript>
                   <![endif]--><!--[if mso]><xml>
                       <w:WordDocument xmlns:w="urn:schemas-microsoft-com:office:word">
                         <w:DontUseAdvancedTypographyReadingMail/>
                       </w:WordDocument>
                       </xml><![endif]-->
                     <style type="text/css">
                   .rollover:hover .rollover-first {
                     max-height:0px!important;
                     display:none!important;
                   }
                   .rollover:hover .rollover-second {
                     max-height:none!important;
                     display:block!important;
                   }
                   .rollover span {
                     font-size:0px;
                   }
                   u + .body img ~ div div {
                     display:none;
                   }
                   #outlook a {
                     padding:0;
                   }
                   span.MsoHyperlink,
                   span.MsoHyperlinkFollowed {
                     color:inherit;
                     mso-style-priority:99;
                   }
                   a.es-button {
                     mso-style-priority:100!important;
                     text-decoration:none!important;
                   }
                   a[x-apple-data-detectors],
                   #MessageViewBody a {
                     color:inherit!important;
                     text-decoration:none!important;
                     font-size:inherit!important;
                     font-family:inherit!important;
                     font-weight:inherit!important;
                     line-height:inherit!important;
                   }
                   .es-desk-hidden {
                     display:none;
                     float:left;
                     overflow:hidden;
                     width:0;
                     max-height:0;
                     line-height:0;
                     mso-hide:all;
                   }
                   @media only screen and (max-width:600px) {.es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-p-default { } *[class="gmail-fix"] { display:none!important } p, a { line-height:150%!important } h1, h1 a { line-height:120%!important } h2, h2 a { line-height:120%!important } h3, h3 a { line-height:120%!important } h4, h4 a { line-height:120%!important } h5, h5 a { line-height:120%!important } h6, h6 a { line-height:120%!important } .es-header-body p { } .es-content-body p { } .es-footer-body p { } .es-infoblock p { } h1 { font-size:36px!important; text-align:left } h2 { font-size:26px!important; text-align:left } h3 { font-size:20px!important; text-align:left } h4 { font-size:24px!important; text-align:left } h5 { font-size:20px!important; text-align:left } h6 { font-size:16px!important; text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:36px!important } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:26px!important } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-header-body h4 a, .es-content-body h4 a, .es-footer-body h4 a { font-size:24px!important } .es-header-body h5 a, .es-content-body h5 a, .es-footer-body h5 a { font-size:20px!important } .es-header-body h6 a, .es-content-body h6 a, .es-footer-body h6 a { font-size:16px!important } .es-menu td a { font-size:12px!important } .es-header-body p, .es-header-body a { font-size:14px!important } .es-content-body p, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body a { font-size:14px!important } .es-infoblock p, .es-infoblock a { font-size:12px!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3, .es-m-txt-c h4, .es-m-txt-c h5, .es-m-txt-c h6 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3, .es-m-txt-r h4, .es-m-txt-r h5, .es-m-txt-r h6 { text-align:right!important } .es-m-txt-j, .es-m-txt-j h1, .es-m-txt-j h2, .es-m-txt-j h3, .es-m-txt-j h4, .es-m-txt-j h5, .es-m-txt-j h6 { text-align:justify!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3, .es-m-txt-l h4, .es-m-txt-l h5, .es-m-txt-l h6 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-m-txt-r .rollover:hover .rollover-second, .es-m-txt-c .rollover:hover .rollover-second, .es-m-txt-l .rollover:hover .rollover-second { display:inline!important } .es-m-txt-r .rollover span, .es-m-txt-c .rollover span, .es-m-txt-l .rollover span { line-height:0!important; font-size:0!important; display:block } .es-spacer { display:inline-table } a.es-button, button.es-button { font-size:20px!important; padding:10px 20px 10px 20px!important; line-height:120%!important } a.es-button, button.es-button, .es-button-border { display:inline-block!important } .es-m-fw, .es-m-fw.es-fw, .es-m-fw .es-button { display:block!important } .es-m-il, .es-m-il .es-button, .es-social, .es-social td, .es-menu { display:inline-block!important } .es-adaptive table, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .adapt-img { width:100%!important; height:auto!important } .es-mobile-hidden, .es-hidden { display:none!important } .es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } .h-auto { height:auto!important } }
                   @media screen and (max-width:384px) {.mail-message-content { width:414px!important } }
                   </style>
                    </head>
                    <body class="body" style="width:100%;height:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0">
                     <div dir="ltr" class="es-wrapper-color" lang="en" style="background-color:#FAFAFA"><!--[if gte mso 9]>
                   			<v:background xmlns:v="urn:schemas-microsoft-com:vml" fill="t">
                   				<v:fill type="tile" color="#fafafa"></v:fill>
                   			</v:background>
                   		<![endif]-->
                      <table width="100%" cellspacing="0" cellpadding="0" class="es-wrapper" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#FAFAFA">
                        <tr>
                         <td valign="top" style="padding:0;Margin:0">
                          <table cellpadding="0" cellspacing="0" align="center" class="es-header" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important;background-color:transparent;background-repeat:repeat;background-position:center top">
                            <tr>
                             <td align="center" style="padding:0;Margin:0">
                              <table bgcolor="#ffffff" align="center" cellpadding="0" cellspacing="0" class="es-header-body" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px">
                                <tr>
                                 <td align="left" style="Margin:0;padding-top:10px;padding-right:20px;padding-bottom:10px;padding-left:20px">
                                  <table cellpadding="0" cellspacing="0" width="100%" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                    <tr>
                                     <td valign="top" align="center" class="es-m-p0r" style="padding:0;Margin:0;width:560px">
                                      <table cellpadding="0" cellspacing="0" width="100%" role="presentation" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                        <tr>
                                         <td align="center" style="padding:0;Margin:0;padding-bottom:10px;padding-top:15px;font-size:0px"><img src="https://ftihdsi.stripocdn.email/content/guids/CABINET_f94cf531612f4f9306fa6bbbfda4e04c6c7cc8af1974f1bc7c42937ff616cd97/images/loremno_bg.png" alt="Logo" width="195" title="Logo" class="adapt-img" style="display:block;font-size:12px;border:0;outline:none;text-decoration:none"></td>
                                        </tr>
                                        <tr>
                                         <td style="padding:0;Margin:0">
                                          <table cellpadding="0" cellspacing="0" width="100%" class="es-menu" role="presentation" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                            <tr class="links">
                                             <td align="center" valign="top" width="25%" style="Margin:0;border:0;padding-top:15px;padding-bottom:15px;padding-right:5px;padding-left:5px">
                                              <div style="vertical-align:middle;display:block">
                                               <a target="_blank" href="" style="mso-line-height-rule:exactly;text-decoration:none;font-family:arial, 'helvetica neue', helvetica, sans-serif;display:block;color:#666666;font-size:14px">Shop</a>
                                              </div></td>
                                             <td align="center" valign="top" width="25%" style="Margin:0;border:0;padding-top:15px;padding-bottom:15px;padding-right:5px;padding-left:5px">
                                              <div style="vertical-align:middle;display:block">
                                               <a target="_blank" href="" style="mso-line-height-rule:exactly;text-decoration:none;font-family:arial, 'helvetica neue', helvetica, sans-serif;display:block;color:#666666;font-size:14px">New</a>
                                              </div></td>
                                             <td align="center" valign="top" width="25%" style="Margin:0;border:0;padding-top:15px;padding-bottom:15px;padding-right:5px;padding-left:5px">
                                              <div style="vertical-align:middle;display:block">
                                               <a target="_blank" href="" style="mso-line-height-rule:exactly;text-decoration:none;font-family:arial, 'helvetica neue', helvetica, sans-serif;display:block;color:#666666;font-size:14px">Sale</a>
                                              </div></td>
                                             <td align="center" valign="top" width="25%" style="Margin:0;border:0;padding-top:15px;padding-bottom:15px;padding-right:5px;padding-left:5px">
                                              <div style="vertical-align:middle;display:block">
                                               <a target="_blank" href="" style="mso-line-height-rule:exactly;text-decoration:none;font-family:arial, 'helvetica neue', helvetica, sans-serif;display:block;color:#666666;font-size:14px">About</a>
                                              </div></td>
                                            </tr>
                                          </table></td>
                                        </tr>
                                      </table></td>
                                    </tr>
                                  </table></td>
                                </tr>
                              </table></td>
                            </tr>
                          </table>
                          <table cellpadding="0" cellspacing="0" align="center" class="es-content" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important">
                            <tr>
                             <td align="center" style="padding:0;Margin:0">
                              <table bgcolor="#ffffff" align="center" cellpadding="0" cellspacing="0" class="es-content-body" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px">
                                <tr>
                                 <td align="left" style="Margin:0;padding-right:20px;padding-bottom:10px;padding-left:20px;padding-top:30px">
                                  <table cellpadding="0" cellspacing="0" width="100%" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                    <tr>
                                     <td align="center" valign="top" style="padding:0;Margin:0;width:560px">
                                      <table cellpadding="0" cellspacing="0" width="100%" role="presentation" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                        <tr>
                                         <td align="center" style="padding:0;Margin:0;padding-top:10px;padding-bottom:10px;font-size:0px"><img src="https://ftihdsi.stripocdn.email/content/guids/CABINET_a3448362093fd4087f87ff42df4565c1/images/78501618239341906.png" alt="" width="100" style="display:block;font-size:14px;border:0;outline:none;text-decoration:none"></td>
                                        </tr>
                                        <tr>
                                         <td align="center" style="padding:0;Margin:0;padding-bottom:10px"><h1 class="es-m-txt-c" style="Margin:0;font-family:arial, 'helvetica neue', helvetica, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:46px;font-style:normal;font-weight:bold;line-height:46px;color:#333333">Welcome {$name}!</h1></td>
                                        </tr>
                                        <tr>
                                         <td align="center" class="es-m-p0r es-m-p0l" style="Margin:0;padding-top:5px;padding-right:40px;padding-bottom:5px;padding-left:40px"><p style="Margin:0;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;letter-spacing:0;color:#333333;font-size:14px">Thanks for trying Lorem Clothing Store. We’re thrilled to have you on board. To get the most out of LOREM, do this next step:</p></td>
                                        </tr>
                                      </table></td>
                                    </tr>
                                  </table></td>
                                </tr>
                                <tr>
                                 <td align="left" style="Margin:0;padding-top:10px;padding-right:20px;padding-bottom:10px;padding-left:20px">
                                  <table cellpadding="0" cellspacing="0" width="100%" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                    <tr>
                                     <td align="center" valign="top" style="padding:0;Margin:0;width:560px">
                                      <table cellpadding="0" cellspacing="0" width="100%" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-left:2px dashed #cccccc;border-right:2px dashed #cccccc;border-top:2px dashed #cccccc;border-bottom:2px dashed #cccccc;border-radius:5px" role="presentation">
                                        <tr>
                                         <td align="center" style="padding:0;Margin:0;padding-right:20px;padding-left:20px;padding-top:20px"><h2 class="es-m-txt-c" style="Margin:0;font-family:arial, 'helvetica neue', helvetica, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:26px;font-style:normal;font-weight:bold;line-height:31.2px;color:#333333">OTP</h2></td>
                                        </tr>
                                        <tr>
                                         <td align="center" style="Margin:0;padding-top:10px;padding-right:20px;padding-left:20px;padding-bottom:20px"><h1 class="es-m-txt-c" style="Margin:0;font-family:arial, 'helvetica neue', helvetica, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:46px;font-style:normal;font-weight:bold;line-height:55.2px;color:#5c68e2"><strong>{$otp}</strong></h1></td>
                                        </tr>
                                      </table></td>
                                    </tr>
                                  </table></td>
                                </tr>
                                <tr>
                                 <td align="left" style="padding:0;Margin:0;padding-right:20px;padding-left:20px;padding-bottom:30px">
                                  <table cellpadding="0" cellspacing="0" width="100%" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                    <tr>
                                     <td align="center" valign="top" style="padding:0;Margin:0;width:560px">
                                      <table cellpadding="0" cellspacing="0" width="100%" role="presentation" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                        <tr>
                                         <td align="center" style="padding:0;Margin:0;padding-top:15px;padding-bottom:15px;font-size:0">
                                          <table cellpadding="0" cellspacing="0" class="es-table-not-adapt es-social" role="presentation" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                            <tr>
                                             <td align="center" valign="top" style="padding:0;Margin:0;padding-right:40px"><img title="Facebook" src="https://ftihdsi.stripocdn.email/content/assets/img/social-icons/logo-black/facebook-logo-black.png" alt="Fb" width="32" style="display:block;font-size:14px;border:0;outline:none;text-decoration:none"></td>
                                             <td align="center" valign="top" style="padding:0;Margin:0;padding-right:40px"><img title="X" src="https://ftihdsi.stripocdn.email/content/assets/img/social-icons/logo-black/x-logo-black.png" alt="X" width="32" style="display:block;font-size:14px;border:0;outline:none;text-decoration:none"></td>
                                             <td align="center" valign="top" style="padding:0;Margin:0;padding-right:40px"><img title="Instagram" src="https://ftihdsi.stripocdn.email/content/assets/img/social-icons/logo-black/instagram-logo-black.png" alt="Inst" width="32" style="display:block;font-size:14px;border:0;outline:none;text-decoration:none"></td>
                                             <td align="center" valign="top" style="padding:0;Margin:0"><img title="Youtube" src="https://ftihdsi.stripocdn.email/content/assets/img/social-icons/logo-black/youtube-logo-black.png" alt="Yt" width="32" style="display:block;font-size:14px;border:0;outline:none;text-decoration:none"></td>
                                            </tr>
                                          </table></td>
                                        </tr>
                                      </table></td>
                                    </tr>
                                  </table></td>
                                </tr>
                              </table></td>
                            </tr>
                          </table>
                          <table cellpadding="0" cellspacing="0" align="center" class="es-footer" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important;background-color:transparent;background-repeat:repeat;background-position:center top">
                            <tr>
                             <td align="center" style="padding:0;Margin:0">
                              <table align="center" cellpadding="0" cellspacing="0" class="es-footer-body" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:640px" role="none">
                                <tr>
                                 <td align="left" style="Margin:0;padding-right:20px;padding-left:20px;padding-top:20px;padding-bottom:20px">
                                  <table cellpadding="0" cellspacing="0" width="100%" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                    <tr>
                                     <td align="left" style="padding:0;Margin:0;width:600px">
                                      <table cellpadding="0" cellspacing="0" width="100%" role="presentation" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                        <tr>
                                         <td align="center" style="padding:0;Margin:0;padding-bottom:35px"><p style="Margin:0;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;letter-spacing:0;color:#333333;font-size:12px">Style Casual&nbsp;© 2021 Style Casual, Inc. All Rights Reserved.</p><p style="Margin:0;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;letter-spacing:0;color:#333333;font-size:12px">4562 Hazy Panda Limits, Chair Crossing, Kentucky, US, 607898</p></td>
                                        </tr>
                                        <tr>
                                         <td style="padding:0;Margin:0">
                                          <table cellpadding="0" cellspacing="0" width="100%" class="es-menu" role="presentation" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                            <tr class="links">
                                             <td align="center" valign="top" width="33.33%" style="Margin:0;border:0;padding-top:5px;padding-bottom:5px;padding-right:5px;padding-left:5px">
                                              <div style="vertical-align:middle;display:block">
                                               <a target="_blank" href="" style="mso-line-height-rule:exactly;text-decoration:none;font-family:arial, 'helvetica neue', helvetica, sans-serif;display:block;color:#999999;font-size:12px">Visit Us </a>
                                              </div></td>
                                             <td align="center" valign="top" width="33.33%" style="Margin:0;border:0;padding-top:5px;padding-bottom:5px;padding-right:5px;padding-left:5px;border-left:1px solid #cccccc">
                                              <div style="vertical-align:middle;display:block">
                                               <a target="_blank" href="" style="mso-line-height-rule:exactly;text-decoration:none;font-family:arial, 'helvetica neue', helvetica, sans-serif;display:block;color:#999999;font-size:12px">Privacy Policy</a>
                                              </div></td>
                                             <td align="center" valign="top" width="33.33%" style="Margin:0;border:0;padding-top:5px;padding-bottom:5px;padding-right:5px;padding-left:5px;border-left:1px solid #cccccc">
                                              <div style="vertical-align:middle;display:block">
                                               <a target="_blank" href="" style="mso-line-height-rule:exactly;text-decoration:none;font-family:arial, 'helvetica neue', helvetica, sans-serif;display:block;color:#999999;font-size:12px">Terms of Use</a>
                                              </div></td>
                                            </tr>
                                          </table></td>
                                        </tr>
                                      </table></td>
                                    </tr>
                                  </table></td>
                                </tr>
                              </table></td>
                            </tr>
                          </table>
                          <table cellpadding="0" cellspacing="0" align="center" class="es-content" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important">
                            <tr>
                             <td align="center" class="es-info-area" style="padding:0;Margin:0">
                              <table align="center" cellpadding="0" cellspacing="0" bgcolor="#00000000" class="es-content-body" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px" role="none">
                                <tr>
                                 <td align="left" style="padding:20px;Margin:0">
                                  <table cellpadding="0" cellspacing="0" width="100%" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                    <tr>
                                     <td align="center" valign="top" style="padding:0;Margin:0;width:560px">
                                      <table cellpadding="0" cellspacing="0" width="100%" role="none" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                                        <tr>
                                         <td align="center" style="padding:0;Margin:0;display:none"></td>
                                        </tr>
                                      </table></td>
                                    </tr>
                                  </table></td>
                                </tr>
                              </table></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table>
                     </div>
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

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject(subject);
        emailRequest.setBody(finalMailContent);

        rabbitMQProducer.sendEmail(emailRequest);
    }

    public void sendWelcomeEmail(String to, String userName, String token) throws MessagingException {
        String finalMailContent = "";
        String subject = "";

        subject += "Welcome to Our Service";
        finalMailContent += welcomeEmailBody.replace("{$name}", userName).replace("{$otp}", token);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject(subject);
        emailRequest.setBody(finalMailContent);

        rabbitMQProducer.sendEmail(emailRequest);

//        jakarta.mail.internet.MimeMessage message = javaMailSender.createMimeMessage();
//
//        message.setFrom("no-reply@lorem.com");
//        message.setRecipients(MimeMessage.RecipientType.TO, to);
//        message.setSubject(subject);
//        message.setContent(finalMailContent, "text/html; charset=utf-8");
//
//        javaMailSender.send(message);
    }

}
