import enums.ProjectStatus;
import model.*;
import repository.implimentation.*;
import repository.interfaces.*;
import service.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Locale;
import java.util.Scanner;

import java.util.List;

public class Main {
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\033[0;1m";

    // Déclaration des services et repositories comme attributs de classe
    private static ClientService clientService;
    private static ProjectService projectService;
    private static MaterialService materialService;
    private static LabelService labelService;
    private static EstimateService estimateService;

    public static void afficherMenu() {
        System.out.println(CYAN + "\n===============================================");
        System.out.println("  🎉 Bienvenue dans l'application de gestion  ");
        System.out.println("     des projets de rénovation de cuisines     ");
        System.out.println("===============================================\n" + RESET);
        System.out.println(GREEN + "              📋 Menu Principal 📋              " + RESET);
        System.out.println(GREEN + "------------------------------------------------" + RESET);
        System.out.println(" 1. 🛠️ Créer un nouveau projet");
        System.out.println(" 2. 📋 Afficher les projets existants");
        System.out.println(" 3. 💰 Calculer le coût d'un projet");
        System.out.println(" 4. ✔️ Accepter ou refuser un devis");
        System.out.println(" 5. ❌ Quitter");
        System.out.println(GREEN + "------------------------------------------------" + RESET);
        System.out.print("Choisissez une option : ");
    }

    public static void afficherSousMenuClient() {
        System.out.println(CYAN + "\n===============================================");
        System.out.println("            --- Recherche de client ---       ");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("===============================================\n" + RESET);
        System.out.println(GREEN + "              📋 Options 📋              " + RESET);
        System.out.println(GREEN + "-----------------------------------------------" + RESET);
        System.out.println(" 1. 🔍 Chercher un client existant");
        System.out.println(" 2. ➕ Ajouter un nouveau client");
        System.out.println(GREEN + "-----------------------------------------------" + RESET);
        System.out.print("Choisissez une option : ");
    }

    public static int rechercherClient(Scanner scanner) {
        System.out.println(CYAN + "\n--- Recherche de client existant ---" + RESET);

        // Validate non-empty input for client name
        String nomClient = "";
        while (nomClient.isEmpty()) {
            System.out.print("Entrez le nom du client : ");
            nomClient = scanner.nextLine().trim();
            if (nomClient.isEmpty()) {
                System.out.println(RED + "Le nom du client ne peut pas être vide. Veuillez réessayer." + RESET);
            }
        }

        // Recherche du client
        Client client = clientService.getClientByName(nomClient);
        if (client != null) {
            System.out.println(CYAN + "\n=== Client trouvé ! ===" + RESET);
            System.out.println(GREEN + "Nom : " + RESET + client.getName());
            System.out.println(GREEN + "Adresse : " + RESET + client.getAddress());
            System.out.println(GREEN + "Numéro de téléphone : " + RESET + client.getPhone());
            System.out.println(CYAN + "========================" + RESET);

            // Demande de confirmation avec validation
            String confirmation = "";
            while (!confirmation.equalsIgnoreCase("y") && !confirmation.equalsIgnoreCase("n")) {
                System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
                confirmation = scanner.nextLine().trim();
                if (!confirmation.equalsIgnoreCase("y") && !confirmation.equalsIgnoreCase("n")) {
                    System.out.println(RED + "Veuillez entrer 'y' pour Oui ou 'n' pour Non." + RESET);
                }
            }

            if (confirmation.equalsIgnoreCase("y")) {
                return client.getId(); // Retourne l'ID si confirmé
            } else {
                System.out.println(RED + "Opération annulée. Retour au sous-menu." + RESET);
            }
        } else {
            System.out.println(RED + "Client non trouvé. Veuillez essayer à nouveau." + RESET);
        }

        return -1; // Retourne -1 si le client n'est pas trouvé ou l'opération annulée
    }

    public static void ajouterNouveauClient(Scanner scanner) {
        System.out.println(CYAN + "\n--- Ajout d'un Nouveau Client ---" + RESET);

        // Enter client details
        String name, address, phone, professionalInput;
        boolean isProfessional = false;

        // Validate name input
        while (true) {
            System.out.print("Entrez le nom du client : ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println(RED + "Erreur : Le nom ne doit pas être vide." + RESET);
            } else {
                break;
            }
        }

        // Validate address input
        while (true) {
            System.out.print("Entrez l'adresse du client : ");
            address = scanner.nextLine().trim();
            if (address.isEmpty()) {
                System.out.println(RED + "Erreur : L'adresse ne doit pas être vide." + RESET);
            } else {
                break;
            }
        }

        // Validate phone input
        while (true) {
            System.out.print("Entrez le numéro de téléphone du client (xx-xx-xx-xx-xx) : ");
            phone = scanner.nextLine().trim();
            if (!phone.matches("\\d{2}-\\d{2}-\\d{2}-\\d{2}-\\d{2}")) {
                System.out.println(RED + "Erreur : Le numéro de téléphone doit respecter le format xx-xx-xx-xx-xx." + RESET);
            } else {
                break;
            }
        }

        // Validate professional status input
        while (true) {
            System.out.print("Le client est-il un professionnel ? (y/n) : ");
            professionalInput = scanner.nextLine().trim();
            if (professionalInput.equalsIgnoreCase("y")) {
                isProfessional = true;
                break;
            } else if (professionalInput.equalsIgnoreCase("n")) {
                isProfessional = false;
                break;
            } else {
                System.out.println(RED + "Erreur : Veuillez entrer 'y' ou 'n'." + RESET);
            }
        }

        // Add client
        clientService.addClient(new Client(0, name, address, phone, isProfessional));
        System.out.println(GREEN + "Client ajouté avec succès !" + RESET);
    }

    public static Project creationNouveauProjet(Scanner scanner, int clientId) {
        System.out.println(CYAN + "\n--- Création d'un Nouveau Projet ---" + RESET);
        // Validate project name input
        String name;
        while (true) {
            System.out.print("Entrez le nom du projet : ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println(RED + "Erreur : Le nom du projet ne doit pas être vide." + RESET);
            } else {
                break;
            }
        }
        // Create project
        Project project = new Project(0, name, 0.0, ProjectStatus.PENDING, 0.0, clientId);
        project = projectService.addProject(project);

        // Confirm project creation
        System.out.println(GREEN + "Projet créé avec succès !" + RESET);

        return project;
    }

    public static void ajouterComposantAuProjet(Scanner scanner, Project project) {
        String choix;

        do {
            System.out.println(CYAN + "\n===============================================");
            System.out.println("            --- Ajouter un Composant ---      ");
            System.out.println("===============================================\n" + RESET);

            System.out.println(GREEN + "              📋 Choix du Composant 📋        " + RESET);
            System.out.println(GREEN + "-----------------------------------------------" + RESET);

            // Validate component type input
            int type;
            while (true) {
                System.out.print("Type de composant (1: Matériau, 2: Main-d'œuvre) : ");
                if (scanner.hasNextInt()) {
                    type = scanner.nextInt();
                    if (type == 1 || type == 2) {
                        break; // Valid input
                    }
                    System.out.println(RED + "Erreur : Veuillez entrer 1 pour Matériau ou 2 pour Main-d'œuvre." + RESET);
                } else {
                    System.out.println(RED + "Erreur : Veuillez entrer un nombre." + RESET);
                    scanner.next(); // Clear invalid input
                }
            }
            scanner.nextLine(); // Consomme le saut de ligne

            // Validate component name
            String name;
            while (true) {
                System.out.print("Entrez le nom du composant : ");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    break;
                } else {
                    System.out.println(RED + "Erreur : Le nom du composant ne doit pas être vide." + RESET);
                }
            }

            // Validate VAT rate
            double vat_rate;
            while (true) {
                System.out.print("Entrez le taux de TVA : ");
                if (scanner.hasNextDouble()) {
                    vat_rate = scanner.nextDouble();
                    if (vat_rate >= 0) {
                        break;
                    }
                }
                System.out.println(RED + "Erreur : Veuillez entrer un taux de TVA valide." + RESET);
                scanner.next(); // Clear invalid input
            }

            // Process material or labor component
            if (type == 1) {
                // Validate material inputs
                System.out.print("Entrez le coût unitaire : ");
                double unit_cost = validateDoubleInput(scanner, "coût unitaire");

                System.out.print("Entrez la quantité : ");
                double quantity = validateDoubleInput(scanner, "quantité");

                System.out.print("Entrez le coût de transport : ");
                double transport_cost = validateDoubleInput(scanner, "coût de transport");

                System.out.print("Entrez le coefficient de qualité (1.0 = standard, > 1.0 = haute qualité) : ");
                double coefficient_quality = validateDoubleInput(scanner, "coefficient de qualité");

                Material material = new Material(0, name, vat_rate, unit_cost, quantity, transport_cost, coefficient_quality, project.getId());
                materialService.addMaterial(material);
                project.addComponent(material); // Adding the material
                System.out.println(GREEN + "Matériau ajouté avec succès !" + RESET);

            } else if (type == 2) {
                // Validate labor inputs
                System.out.print("Entrez le taux horaire : ");
                double hourly_rate = validateDoubleInput(scanner, "taux horaire");

                System.out.print("Entrez le nombre d'heures de travail : ");
                double hours_work = validateDoubleInput(scanner, "nombre d'heures de travail");

                System.out.print("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
                double worker_productivity = validateDoubleInput(scanner, "facteur de productivité");

                Labor labor = new Labor(0, name, vat_rate, project.getId(), hourly_rate, hours_work, worker_productivity);
                labelService.addLabel(labor);
                project.addComponent(labor); // Adding the labor
                System.out.println(GREEN + "Main-d'œuvre ajoutée avec succès !" + RESET);
            }

            System.out.print(CYAN + "Voulez-vous ajouter un autre composant ? (y/n) : " + RESET);
            choix = scanner.next();
            scanner.nextLine(); // Consommer le saut de ligne

            // Validation de l'entrée pour 'y' ou 'n'
            while (!choix.equalsIgnoreCase("y") && !choix.equalsIgnoreCase("n")) {
                System.out.println(RED + "Erreur : Veuillez entrer 'y' pour Oui ou 'n' pour Non." + RESET);
                System.out.print(CYAN + "Voulez-vous ajouter un autre composant ? (y/n) : " + RESET);
                choix = scanner.next();
                scanner.nextLine(); // Consommer le saut de ligne
            }


        } while (choix.equalsIgnoreCase("y"));

        System.out.println(GREEN + "Ajout de composants terminé." + RESET);
    }

    public static void calculerCoutTotal(Project project, Scanner scanner) {
        System.out.println(CYAN + "\n--- Calcul du Coût Total ---" + RESET);

        // Application de la TVA pour le projet
        String applyVAT = getYesNoInput(scanner, "Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
        double vatPercentage = 0;
        if (applyVAT.equalsIgnoreCase("y")) {
            vatPercentage = getValidDoubleInput(scanner, "pourcentage de TVA (%)");
        }

        // Application de la marge bénéficiaire
        String applyMargin = getYesNoInput(scanner, "Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
        double marginPercentage = 0;
        if (applyMargin.equalsIgnoreCase("y")) {
            marginPercentage = getValidDoubleInput(scanner, "pourcentage de marge bénéficiaire (%)");
        }

        // Affichage des informations sur le projet avant calcul des coûts
        System.out.println(CYAN + "\n--- Résultat du Calcul ---" + RESET);
        System.out.println("Nom du projet : " + project.getName());

        Client client = clientService.getClientById(project.getClient_id()); // Exemple de récupération du client
        System.out.println("Client : " + client.getName());
        System.out.println("Adresse du chantier : " + client.getAddress());

        // Calcul des coûts
        List<Material> materials = (List<Material>) project.getComponents().get(Material.class);
        double totalMaterialCost = materials.stream()
                .mapToDouble(material -> material.getUnit_cost() * material.getQuantity() * material.getCoefficient_quality() + material.getTransport_cost())
                .sum();

        List<Labor> labors = (List<Labor>) project.getComponents().get(Labor.class);
        double totalLaborCost = labors.stream()
                .mapToDouble(labor -> labor.getHourly_rate() * labor.getHours_work() * labor.getWorker_productivity())
                .sum();

        // Affichage des détails des matériaux
        System.out.println(GREEN + "--- Détail des Coûts ---" + RESET);
        System.out.println("1. Matériaux :");
        materials.forEach(material -> {
            System.out.println(String.format("- %s : %.2f € ( vat rate : %.2f , quantité : %.2f , coût unitaire : %.2f €/unité , qualité : %.1f , transport : %.2f € )",
                    material.getName(),
                    material.getUnit_cost() * material.getQuantity() * material.getCoefficient_quality() + material.getTransport_cost(),
                    material.getVat_rate(),
                    material.getQuantity(),
                    material.getUnit_cost(),
                    material.getCoefficient_quality(),
                    material.getTransport_cost()));
        });
        System.out.println(String.format(BOLD + "**Coût total des matériaux avant TVA : %.2f €**" + RESET, totalMaterialCost));

        // Appliquer la TVA sur les matériaux
        double totalMaterialCostTaxed = totalMaterialCost + (totalMaterialCost * (vatPercentage / 100));
        System.out.println(String.format(BOLD + "**Coût total des matériaux avec TVA (%.2f%%) : %.2f €**" + RESET, vatPercentage, totalMaterialCostTaxed));

        // Afficher les détails de la main-d'œuvre
        System.out.println("2. Main-d'œuvre :");
        labors.forEach(labor -> {
            System.out.println(String.format("- %s : %.2f € ( vat rate : %.2f , taux horaire : %.2f €/h, heures travaillées : %.2f h, productivité : %.1f)",
                    labor.getName(),
                    labor.getHourly_rate() * labor.getHours_work() * labor.getWorker_productivity(),
                    labor.getVat_rate(),
                    labor.getHourly_rate(),
                    labor.getHours_work(),
                    labor.getWorker_productivity()));
        });
        System.out.println(String.format(BOLD + "**Coût total de la main-d'œuvre avant TVA : %.2f €**" + RESET, totalLaborCost));

        // Appliquer la TVA sur la main-d'œuvre
        double totalLaborCostTaxed = totalLaborCost + (totalLaborCost * (vatPercentage / 100));
        System.out.println(String.format(BOLD + "**Coût total de la main-d'œuvre avec TVA (%.2f%%) : %.2f €**" + RESET, vatPercentage, totalLaborCostTaxed));

        // Calcul du coût total avant marge bénéficiaire
        double totalCostProject = totalMaterialCostTaxed + totalLaborCostTaxed;
        System.out.println(String.format(BOLD + "3. Coût total avant marge : %.2f €" + RESET, totalCostProject));

        double margin = 0;
        // Appliquer la marge bénéficiaire
        if (applyMargin.equalsIgnoreCase("y")) {
            margin = totalCostProject * (marginPercentage / 100);
            System.out.println(String.format(BOLD + "4. Marge bénéficiaire (%.2f%%) : %.2f €" + RESET, marginPercentage, margin));
            totalCostProject += margin;
        }

        // Afficher le coût total final du projet
        System.out.println(String.format(BOLD + "**Coût total final du projet : %.2f €**" + RESET, totalCostProject));

        // Mettre à jour de la marge beneficiaire du projet dans la base de données
        project.setProfit_margin(margin);
        project.setTotal_cost(totalCostProject);

        // Appelle la méthode updateProject pour sauvegarder les modifications
        projectService.updateProject(project.getId(), project);
    }

    public static void enregistrerDevis(Project project, Scanner scanner) {
        System.out.println(CYAN + "\n--- Enregistrement du Devis ---" + RESET);

        Date dateEmission = null;
        Date dateValidity = null;

        // Demander et valider les dates d'émission et de validité
        while (true) {
            // Demander la date d'émission
            System.out.print("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
            String dateEmissionStr = scanner.nextLine();
            dateEmission = parseDate(dateEmissionStr);

            // Demander la date de validité
            System.out.print("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
            String dateValidityStr = scanner.nextLine();
            dateValidity = parseDate(dateValidityStr);

            // Vérification des dates
            if (dateEmission == null || dateValidity == null) {
                System.out.println(RED + "Erreur : Veuillez entrer des dates valides dans le format jj/mm/aaaa." + RESET);
            } else if (!dateValidity.after(dateEmission)) {
                System.out.println(RED + "Erreur : La date de validité doit être postérieure à la date d'émission." + RESET);
            } else {
                // Si tout est valide, on sort de la boucle
                break;
            }
        }

        // Demander si l'utilisateur souhaite enregistrer le devis
        String saveEstimate = getYesNoInput(scanner, "Souhaitez-vous enregistrer le devis ? (y/n) : ");

        if (saveEstimate.equalsIgnoreCase("y")) {
            // Créer l'objet Estimate
            Estimate estimate = new Estimate(
                    0, // L'ID sera généré lors de l'enregistrement
                    project.getTotal_cost(), // Montant du devis basé sur le coût total
                    dateEmission,
                    dateValidity,
                    null,// Le devis n'est pas encore accepté par défaut
                    project.getId() // ID du projet lié
            );

            // Sauvegarder l'estimate via le service approprié
            estimateService.addEstimate(estimate);

            System.out.println(GREEN + "Devis enregistré avec succès !" + RESET);
        } else {
            System.out.println(RED + "Le devis n'a pas été enregistré." + RESET);
        }

        System.out.println(CYAN + "--- Fin du projet ---" + RESET);
    }

    public static void afficherMenuDevis(Scanner scanner, EstimateService estimateService) {
        System.out.println(CYAN + "\n===============================================");
        System.out.println("          --- Accepter ou Refuser un Devis ---");
        System.out.println("===============================================\n" + RESET);

        // Validation de l'ID du devis
        int estimateId;
        while (true) {
            System.out.print("Entrez l'ID du devis : ");
            if (scanner.hasNextInt()) {
                estimateId = scanner.nextInt();
                scanner.nextLine(); // Consommer la ligne vide
                break; // ID valide, on sort de la boucle
            } else {
                System.out.println(RED + "Erreur : Veuillez entrer un nombre entier pour l'ID." + RESET);
                scanner.next(); // Consommer l'entrée invalide
            }
        }

        // Récupération du devis correspondant à l'ID
        Estimate estimate = estimateService.getEstimateById(estimateId);
        if (estimate == null) {
            System.out.println(RED + "Erreur : Aucun devis trouvé avec l'ID " + estimateId + "." + RESET);
            return; // Sortir si le devis n'est pas trouvé
        }

        // Validation de la décision (acceptation ou refus)
        String decision;
        while (true) {
            System.out.print("Souhaitez-vous accepter (a) ou refuser (r) ce devis ? ");
            decision = scanner.nextLine().trim().toLowerCase();
            if (decision.equals("a") || decision.equals("r")) {
                break; // Choix valide, on sort de la boucle
            } else {
                System.out.println(RED + "Erreur : Veuillez entrer 'a' pour accepter ou 'r' pour refuser." + RESET);
            }
        }

        Project project = projectService.getProjectById(estimate.getProject_id());
        // Traitement de la décision
        if (decision.equals("a")) {
            // Mise à jour pour accepter le devis
            estimate.setIs_accepted(true);
            estimateService.updateEstimate(estimateId, estimate);
            System.out.println(GREEN + "Devis accepté avec succès !" + RESET);

            // Mise a jour du status du projet
            project.setState_project(ProjectStatus.InPROGRESS);

        } else {
            // Mise à jour pour refuser le devis (ou autre logique de refus)
            estimate.setIs_accepted(false);
            estimateService.updateEstimate(estimateId, estimate);
            System.out.println(RED + "Devis refusé." + RESET);

            // Mise a jour du status du projet
            project.setState_project(ProjectStatus.CANCELED);
        }
        projectService.updateProject(project.getId(), project);
    }

    // Helper method to get yes/no input
    private static String getYesNoInput(Scanner scanner, String prompt) {
        String response;
        while (true) {
            System.out.print(prompt);
            response = scanner.nextLine().trim();
            if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("n")) {
                return response;
            }
            System.out.println(RED + "Erreur : Veuillez entrer 'y' ou 'n'." + RESET);
        }
    }

    // Helper method to validate double input
    private static double getValidDoubleInput(Scanner scanner, String fieldName) {
        double value;
        while (true) {
            System.out.print("Entrez " + fieldName + ": ");
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                if (value >= 0) {
                    scanner.nextLine(); // Consomme le saut de ligne
                    return value;
                }
            }
            System.out.println(RED + "Erreur : Veuillez entrer une valeur valide pour " + fieldName + "." + RESET);
            scanner.next(); // Clear invalid input
        }
    }

    // Helper method to validate double input
    private static double validateDoubleInput(Scanner scanner, String fieldName) {
        double value;
        while (true) {
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                if (value >= 0) {
                    return value;
                }
            }
            System.out.println(RED + "Erreur : Veuillez entrer une valeur valide pour " + fieldName + "." + RESET);
            scanner.next(); // Clear invalid input
        }
    }

    // Méthode utilitaire pour convertir une date String en java.sql.Date
    private static Date parseDate(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return new Date(format.parse(dateStr).getTime());
        } catch (ParseException e) {
            System.out.println(RED + "Erreur : Format de date invalide." + RESET);
            return null;
        }
    }


    public static void main(String[] args) throws SQLException {
        // Initialisation des repositories
        ClientRepositoryInterface clientRepository = new ClientRepository();
        ProjectRepositoryInterface projectRepository = new ProjectRepository();
        MaterialRepositoryInterface materialRepository = new MaterialRepository();
        LaborRepositoryInterface labelRepository = new LaborRepository();
        EstimateRepositoryInterface estimateRepository = new EstimateRepository();

        // Initialisation des services
        clientService = new ClientService(clientRepository);
        projectService = new ProjectService(projectRepository);
        materialService = new MaterialService(materialRepository);
        labelService = new LabelService(labelRepository);
        estimateService = new EstimateService(estimateRepository);


        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        while (true) {

            // Render main menu
            int choice;
            while (true) {
                afficherMenu();
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consomme le saut de ligne

                    // Valider que l'entrée est entre 1 et 5
                    if (choice >= 1 && choice <= 5) {
                        break; // Input valide, on sort de la boucle
                    } else {
                        System.out.println(RED + "Erreur : Veuillez entrer un nombre entre 1 et 4." + RESET);
                    }
                } else {
                    System.out.println(RED + "Erreur : Veuillez entrer un nombre valide." + RESET);
                    scanner.next(); // Consommer l'entrée incorrecte
                }
            }


            switch (choice) {
                case 1:
                    boolean clientExisted = false;
                    int clientId = -1;

                    while (!clientExisted) {

                        // Render sub-menu for client search or add
                        int subChoice;
                        while (true) {
                            afficherSousMenuClient();
                            if (scanner.hasNextInt()) {
                                subChoice = scanner.nextInt();
                                scanner.nextLine(); // Consomme le saut de ligne

                                // Valider que l'entrée est 1 ou 2
                                if (subChoice == 1 || subChoice == 2) {
                                    break; // Input valide, on sort de la boucle
                                } else {
                                    System.out.println(RED + "Erreur : Veuillez entrer 1 pour chercher un client ou 2 pour ajouter un nouveau client." + RESET);
                                }
                            } else {
                                System.out.println(RED + "Erreur : Veuillez entrer un nombre valide." + RESET);
                                scanner.next(); // Consommer l'entrée incorrecte
                            }
                        }


                        if (subChoice == 1) {
                            // Search for an existing client
                            clientId = rechercherClient(scanner);

                            if (clientId != -1) {
                                // Client found
                                clientExisted = true;
                                System.out.println(GREEN + "Client trouvé avec succès !" + RESET);

                            }

                        } else if (subChoice == 2) {
                            // Add a new client
                            ajouterNouveauClient(scanner);

                        } else {
                            // Invalid option in sub-menu
                            System.out.println(RED + "Erreur : Option invalide. Veuillez réessayer." + RESET);
                        }
                    }

                    // Create a new project after a valid client is found
                    Project projectCreated = creationNouveauProjet(scanner, clientId);

                    // Add components to the project
                    ajouterComposantAuProjet(scanner, projectCreated);

                    // Calculate the total cost of the project
                    calculerCoutTotal(projectCreated, scanner);

                    // Create an estimate for the project
                    enregistrerDevis(projectCreated, scanner);

                    break;

                case 2:
                    System.out.println(CYAN + "\n===============================================");
                    System.out.println("         📋 Liste des Projets Existants        ");
                    System.out.println("===============================================\n" + RESET);

                    List<Project> projects = projectService.getAllProjects();

                    if (projects.isEmpty()) {
                        System.out.println(RED + "Aucun projet disponible." + RESET);
                    } else {
                        for (Project project : projects) {
                            Client client = project.getClient(clientService);
                            System.out.println("Projet ID: " + project.getId() + " | Nom: " + project.getName() + " | Client: " + client.getName() + " | État: " + project.getState_project() + " | Coût total: " + project.getTotal_cost() + " €");
                        }
                    }
                    break;
                case 3:
                    // Logique pour calculer le coût d'un projet
                    break;
                case 4:
                    // Logique pour accepter ou refuser un devis
                    afficherMenuDevis(scanner, estimateService);
                    break;
                case 5:
                    System.out.println("👋 Merci d'avoir utilisé notre application. À bientôt !");
                    System.exit(0);
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }
}
