import java.io.*;
import java.util.*;

public class GestionEtudiant {

    static final String fichier = "etudiant.txt";

    public static void main(String[] args) {
        Scanner saisir = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\n==== Menu ====");
            System.out.println("1. Ajouter un étudiant");
            System.out.println("2. Lister les étudiants");
            System.out.println("3. Consulter un étudiant");
            System.out.println("4. Modifier un étudiant");
            System.out.println("5. Quitter");

            System.out.print("Votre choix : ");
            choix = saisir.nextInt();
            saisir.nextLine(); // vider le buffer

            switch (choix) {
                case 1:
                    ajouterEtudiant(saisir);
                    break;
                case 2:
                    listerEtudiants();
                    break;
                case 3:
                    consulterEtudiant(saisir);
                    break;
                case 4:
                    modifierEtudiant(saisir);
                    break;
                case 5:
                    System.out.println("Fin du programme.");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 5);

        saisir.close();
    }

    static void ajouterEtudiant(Scanner saisir) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true))) {
            System.out.print("Matricule : ");
            String matricule = saisir.nextLine();

            System.out.print("Nom : ");
            String nom = saisir.nextLine();

            System.out.print("Prénom : ");
            String prenom = saisir.nextLine();

            System.out.print("Classe : ");
            String classe = saisir.nextLine();

            writer.write(matricule + "," + nom + "," + prenom + "," + classe);
            writer.newLine();

            System.out.println("✅ Étudiant enregistré avec succès !");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l’enregistrement : " + e.getMessage());
        }
    }

    static void listerEtudiants() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            System.out.println("\n--- Liste des étudiants ---");
            while ((ligne = reader.readLine()) != null) {
                String[] infos = ligne.split(",");
                if (infos.length >= 4) {
                    System.out.println("Matricule : " + infos[0] + " | Nom : " + infos[1] + " | Prénom : " + infos[2] + " | Classe : " + infos[3]);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    static void consulterEtudiant(Scanner saisir) {
        System.out.print("Entrez le matricule de l’étudiant à consulter : ");
        String matriculeRecherche = saisir.nextLine();
        boolean trouve = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] infos = ligne.split(",");
                if (infos.length >= 4 && infos[0].equalsIgnoreCase(matriculeRecherche)) {
                    System.out.println("\n✅ Étudiant trouvé :");
                    System.out.println("Matricule : " + infos[0]);
                    System.out.println("Nom : " + infos[1]);
                    System.out.println("Prénom : " + infos[2]);
                    System.out.println("Classe : " + infos[3]);
                    trouve = true;
                    break;
                }
            }
            if (!trouve) {
                System.out.println("❌ Aucun étudiant trouvé avec ce matricule.");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    // ✅ Modifier un étudiant existant
    static void modifierEtudiant(Scanner saisir) {
        System.out.print("Entrez le matricule de l’étudiant à modifier : ");
        String matriculeRecherche = saisir.nextLine();
        boolean modifie = false;

        List<String> lignes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] infos = ligne.split(",");
                if (infos.length >= 4 && infos[0].equalsIgnoreCase(matriculeRecherche)) {
                    // Étudiant trouvé, on demande les nouvelles infos
                    System.out.println("Étudiant trouvé. Entrez les nouvelles informations :");

                    System.out.print("Nouveau nom : ");
                    String nom = saisir.nextLine();

                    System.out.print("Nouveau prénom : ");
                    String prenom = saisir.nextLine();

                    System.out.print("Nouvelle classe : ");
                    String classe = saisir.nextLine();

                    lignes.add(matriculeRecherche + "," + nom + "," + prenom + "," + classe);
                    modifie = true;
                } else {
                    lignes.add(ligne); // garder les autres lignes
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture : " + e.getMessage());
            return;
        }

        if (!modifie) {
            System.out.println("❌ Aucun étudiant trouvé avec ce matricule.");
            return;
        }

        // Réécriture du fichier avec les données mises à jour
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            for (String l : lignes) {
                writer.write(l);
                writer.newLine();
            }
            System.out.println("✅ Étudiant modifié avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture : " + e.getMessage());
        }
    }
}
