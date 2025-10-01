package service;

import domain.Portefeuille;
import domain.enums.TypeCrypto;
import repository.PortefeuilleRepository;
import util.GenerateurAdresse;
import java.util.List;
import java.util.Optional;


public class ServicePortefeuille {
    
    private final PortefeuilleRepository portefeuilleRepository;
    
    public ServicePortefeuille() {
        this.portefeuilleRepository = new PortefeuilleRepository();
    }
    
    public Portefeuille creerPortefeuille(TypeCrypto typeCrypto) {
        String adresse = GenerateurAdresse.genererAdresse(typeCrypto);
        Portefeuille portefeuille = new Portefeuille(typeCrypto, adresse);
        return portefeuilleRepository.sauvegarder(portefeuille);
    }
    
    public Optional<Portefeuille> obtenirPortefeuilleParId(String id) {
        return portefeuilleRepository.trouverParId(id);
    }
    
    public List<Portefeuille> obtenirTousLesPortefeuilles() {
        return portefeuilleRepository.trouverTous();
    }
    
    public boolean verifierExistencePortefeuille(String id) {
        return portefeuilleRepository.trouverParId(id).isPresent();
    }
}