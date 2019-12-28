package hdn.projects.gestiondevis.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hdn.projects.gestiondevis.dao.IUtilisateurRepository;
import hdn.projects.gestiondevis.entities.Utilisateur;
import hdn.projects.gestiondevis.exception.GestionDevisException;
import hdn.projects.gestiondevis.utils.EtatOperation;
import hdn.projects.gestiondevis.utils.Utilitaire;

/**
 * @author Gandalf
 *
 */

// l'annotation @Service est optionnelle ici, car il n'existe qu'une
// seule implémentation de l'interface IUtilisateur
@Service(value = "utilisateurService")
public class UtilisateurImpl implements IUtilisateur {

	private static final Logger logger = LoggerFactory.getLogger(UtilisateurImpl.class);

	@Autowired
	private IUtilisateurRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional(readOnly = false)
	public Utilisateur saveOrUpdateEntity(Utilisateur utilisateur, EtatOperation typeOperation) throws GestionDevisException {

		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), utilisateur);

		switch (typeOperation) {

		case CREATE: {
			// le user ne doit pas exister
			Utilisateur userDB = this.getEntity(utilisateur.getLogin());
			if (utilisateur.equals(userDB)) {
				// le user avec ce login existe
				throw new GestionDevisException("Le user avec le login " + utilisateur.getLogin() + " existe déja",
						HttpStatus.CONFLICT);
			}
			utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));
			try {
				return userRepository.save(utilisateur);
			} catch (Exception ex) {
				throw new GestionDevisException("Erreur de création de l'utilisateur: " + utilisateur.getLogin(),
						ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		case UPDATE: {
			// le user doit exister -> on remonte une exception a defaut
			this.getEntityWithOptional(utilisateur.getId());
			try {
				return userRepository.save(utilisateur);
			} catch (Exception ex) {
				throw new GestionDevisException("Erreur de création de l'utilisateur: " + utilisateur.getLogin(),
						ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		default: {
			return null;
		}
		}
		

	}

	@Override
	@Transactional(readOnly = true)
	public List<Utilisateur> getEntities() throws GestionDevisException {
		List<Utilisateur> users = userRepository.getAllUsersStream().collect(Collectors.toList());
		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), users);
		return users;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Utilisateur> getEntitiesFrom(Long idUser) throws GestionDevisException {

		// verif si le user existe
		this.getEntityWithOptional(idUser);

		List<Utilisateur> listUser = userRepository.getClientsStream(idUser).collect(Collectors.toList());

		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), listUser);
		return listUser;
	}

	@Override
	@Transactional(readOnly = true)
	public Utilisateur getEntity(Long refClient) throws GestionDevisException {
		return userRepository.getOne(refClient);
	}

	@Override
	@Transactional(readOnly = true)
	public Utilisateur getEntityWithOptional(Long refClient) throws GestionDevisException {
		Optional<Utilisateur> optUser = userRepository.findById(refClient);
		optUser.orElseThrow(() -> new GestionDevisException("Get User Error",
				"Erreur de recuperation de l'utilisateur avec id : " + refClient, HttpStatus.NOT_FOUND));
		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), optUser.get());
		return optUser.get();
	}

	@Override
	@Transactional(readOnly = true)
	public Utilisateur getEntity(String login) throws GestionDevisException {
		Optional<Utilisateur> optUser = userRepository.findByLoginPassword(login);
		optUser.orElseThrow(() -> new GestionDevisException("Get User Error",
				"Erreur de recuperation de l'utilisateur avec le login : " + login, HttpStatus.NOT_FOUND));
		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), optUser.get());
		return optUser.get();
	}

	@Override
	@Transactional(readOnly = false)
	public EtatOperation deleteEntity(Long refEntity) throws GestionDevisException {

		this.getEntityWithOptional(refEntity);
		//
		userRepository.deleteById(refEntity);
		Utilitaire.loguer(logger, this.getClass().toString(), new Object() {
		}.getClass().getEnclosingMethod().getName(), refEntity);
		return EtatOperation.VALID;
	}

	@Override
	public boolean contacterEntity(Utilisateur client, String message) throws GestionDevisException {
		// TODO Auto-generated method stub
		/*
		 * SimpleMailMessage mail = new SimpleMailMessage();
		 * mail.setFrom(loanMailDto.MAIL_FROM); mail.setTo(customer.getEmail());
		 * mail.setSentDate(new Date()); mail.setSubject(loanMailDto.getEmailSubject());
		 * mail.setText(loanMailDto.getEmailContent()); javaMailSender.send(mail); try {
		 * javaMailSender.send(mail); } catch (MailException e) { return new
		 * ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN); }
		 */
		return false;

	}
}
