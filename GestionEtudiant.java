import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestionEtudiant {

    // Nom du fichier où seront stockés les étudiants
    static final String fichier = "etudiant.txt";

    public static void main(String[] args) {
        Scanner saisir = new Scanner(System.in);

        System.out.println("=== Ajout d’un nouvel étudiant ===");

        ajouterEtudiant(saisir);

        saisir.close();
    }

    // Méthode pour ajouter un étudiant
    static void ajouterEtudiant(Scanner saisir) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true))) {
            System.out.print("Matricule : ");
            String matricule = saisir.nextLine();

            System.out.print("Nom : ");
            String nom = saisir.nextLine();

            System.out.print("Prénom : ");
            String prenom = saisir.nextLine();

             System.out.print("Classe : ");
            String Classe = saisir.nextLine();

            // Écriture dans le fichier : matricule,nom,prenom
            writer.write(matricule + "," + nom + "," + prenom + ","+ Classe);
            writer.newLine();

            System.out.println("✅ Étudiant enregistré avec succès !");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l’enregistrement : " + e.getMessage());
        }
    }
}
