package pe.edu.estubeca.estubeca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.estubeca.estubeca.entities.Profile;
import pe.edu.estubeca.estubeca.entities.User;
import pe.edu.estubeca.estubeca.exception.ResourceNotFoundException;
import pe.edu.estubeca.estubeca.repository.ProfileRepository;
import pe.edu.estubeca.estubeca.repository.UserRepository;
import pe.edu.estubeca.estubeca.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProfileController {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/profiles")
    @Transactional (readOnly = true)
    public ResponseEntity<List<Profile>> getAllProfiles(){

        List<Profile> profiles=profileRepository.findAll();
        List<Profile> profilesAux=new ArrayList<>();

        profilesAux=profileRepository.findAll();

        if(profilesAux.size()>0){
            profilesAux.stream().forEach((p)->{
                byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
                p.setPicture(imageDescompressed);
                profiles.add(p);
            });
        }
        return new ResponseEntity<List<Profile>>(profiles, HttpStatus.OK);
    }
    //GET=>http:localthost:8080/api/profile/1
    @GetMapping("/profiles/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Profile> getProfileById(@PathVariable("id") Long id){
        Profile profile= profileRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Not found profile with id="+id));

        if(profile!=null){
            byte[] imageDescompressed = Util.decompressZLib(profile.getPicture());
            profile.setPicture(imageDescompressed);
        }

        return new ResponseEntity<Profile>(profile,HttpStatus.OK);
    }

    @PostMapping("/profiles")
    @Transactional
    public ResponseEntity<Profile> createProfile(@RequestParam("picture") MultipartFile picture,
                                                 @RequestParam("userId") Long userId,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("lastname") String lastname,
                                                 @RequestParam("phone") String phone,
                                                 @RequestParam("grade") String grade) throws IOException {
        Profile profile = new Profile();
        profile.setName(name);
        profile.setLastName(lastname);
        profile.setPhone(phone);
        profile.setGrade(grade);
        profile.setPicture(Util.compressZLib(picture.getBytes()));

        //TODO: búsqueda del userid para estableceren el objeto de profile
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id="+userId));

        if(user!=null){
            profile.setUser(user);
        }

        Profile profileSaved=profileRepository.save(profile);

        return new ResponseEntity<Profile>(profileSaved,HttpStatus.CREATED);
    }
   /* public ResponseEntity<Profile> createProfile(@RequestBody Profile profile){
        Profile newProfile=
                profileRepository.save(
                        new Profile(
                                profile.getId(),
                                profile.getUser(),
                                profile.getName(),
                                profile.getLastName(),
                                profile.getPhone(),
                                profile.getImgUrl(),
                                profile.getGrade()
                               )
                );
        return new ResponseEntity<Profile>(newProfile,HttpStatus.CREATED);
    }*/
    //PUT=>http:localthost:8080/api/profiles/1
    /*@PutMapping("/profiles/{id}")
    public ResponseEntity<Profile> updateProfile(
            @PathVariable("id") Long id,
            @RequestBody Profile profile){
        Profile profileUpdate= profileRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Not found post with id="+id));

       profileUpdate.setName(profile.getName());
       profileUpdate.setLastName(profile.getLastName());
       profileUpdate.setPhone(profile.getPhone());
       profileUpdate.setGrade(profile.getGrade());
       profileUpdate.setImgUrl(profile.getImgUrl());

        return new ResponseEntity<Profile>(profileRepository.save(profileUpdate),
                HttpStatus.OK);
    }*/

}
