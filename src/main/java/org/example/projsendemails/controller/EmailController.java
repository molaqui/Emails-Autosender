package org.example.projsendemails.controller;

import org.example.projsendemails.service.EmailService;
import org.example.projsendemails.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendEmails")
    public String sendEmails() {
        try {
            // Charger la liste des emails depuis le fichier dans resources
            List<String> emails = EmailUtils.readEmailsFromFile("emails.txt");

            // Récupérer le chemin du CV depuis resources
            String cvPath = EmailUtils.getResourceFilePath("CV_Abderrahim_El_Mellaqui.pdf");

            // Envoyer les emails un par un
            for (String email : emails) {
                emailService.sendEmailWithAttachment(
                        email.trim(),
                        "Candidature pour le Stage PFE – Développement Informatique",
                        "Bonjour,\n\n" +
                                "Je suis Abderrahim El Mellaqui, étudiant en dernière année de Génie Informatique à l'ENSA de Marrakech. " +
                                "Passionné par les technologies web, j'ai acquis une solide expertise en Java, Spring Boot, et Angular, " +
                                "que j’ai appliquée dans des projets concrets.\n\n" +
                                "Je serais ravi de contribuer à vos projets innovants et de rejoindre vos équipes. " +
                                "Vous trouverez mon CV en pièce jointe pour plus de détails.\n\n" +
                                "Cordialement,\n" +
                                "Abderrahim El Mellaqui\n" +
                                "abdrahimmolaqui@gmail.com",
                        cvPath
                );

                // Pause de 2 secondes entre les envois
                Thread.sleep(2000);
            }

            return "Emails envoyés avec succès !";

        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de l'envoi des emails : " + e.getMessage();
        }
    }
}
