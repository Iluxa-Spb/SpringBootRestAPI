package com.example.RestTest.controller;

import com.example.RestTest.dao.OtherUserRepository;
import com.example.RestTest.dao.OtherUserRepositoryImpl;
import com.example.RestTest.exception.EntityNotFoundException;
import com.example.RestTest.exception.FieldValidationError;
import com.example.RestTest.model.OtherUser;
import com.example.RestTest.model.PhoneBookEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/")
public class UsersController {
    private OtherUserRepository users_ = new OtherUserRepositoryImpl();

    @GetMapping
    public ResponseEntity<List<String>> list() throws EntityNotFoundException {
        List<String> list = new ArrayList<String>();
        if (users_.findAllUsers().isEmpty()){
            throw new EntityNotFoundException();
        } else {
            for (int i = 0; i < users_.findAllUsers().size(); i++) {
                list.add("ID:"+users_.findAllUsers().get(i).getId()+" "+users_.findAllUsers().get(i).getUserName());
            }
        }
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<String>> getOne(@PathVariable String id) throws EntityNotFoundException{
        List<String> list = new ArrayList<>();
        OtherUser user = users_.getUserById(id);
        list.add(user.getId());
        list.add(user.getUserName());
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<OtherUser> create(@RequestBody @Valid OtherUser user) throws EntityNotFoundException, Exception{
        if(user.getId()==null||user.getUserName()==null) {
            throw new EntityNotFoundException();
        }else {
            if (users_.getUserById(user.getId())!=null){
                throw new Exception("This's ID already in use");
            }else {
                users_.findAllUsers().add(user);
                return new ResponseEntity<OtherUser>(user, HttpStatus.CREATED);
            }
        }
    }

    @GetMapping("create")
    public ResponseEntity<OtherUser> createParam(@RequestParam String id,
                                                 @RequestParam String userName) throws EntityNotFoundException, Exception{
        OtherUser user = new OtherUser(id, userName);
        if (user.getId()==null||user.getUserName()==null) {
            throw new EntityNotFoundException();
        }else {
            if (users_.getUserById(user.getId())!=null){
                throw new Exception("This's ID already in use");
            }else {
                users_.findAllUsers().add(user);
                return new ResponseEntity<OtherUser>(user, HttpStatus.CREATED);
            }
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<OtherUser> update(@PathVariable String id, @RequestBody @Valid OtherUser otherUser) throws  EntityNotFoundException{
        if(otherUser.getId()==null||otherUser.getUserName()==null) {
            throw new EntityNotFoundException();
        }else {
            users_.findAllUsers().set(users_.findAllUsers().indexOf(users_.getUserById(id)), otherUser);
            return new ResponseEntity<OtherUser>(otherUser, HttpStatus.OK);
        }
    }

    @GetMapping("{id}/update")
    public ResponseEntity<OtherUser> updateParam(@PathVariable String id,
                                                 @RequestParam("id") String userID,
                                                 @RequestParam("userName") String userName ) throws  EntityNotFoundException{
        OtherUser otherUser = new OtherUser(userID,userName);
        if(otherUser.getId()==null||otherUser.getUserName()==null) {
            throw new EntityNotFoundException();
        }else {
            users_.findAllUsers().set(users_.findAllUsers().indexOf(users_.getUserById(id)), otherUser);
            return new ResponseEntity<OtherUser>(otherUser, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) throws EntityNotFoundException{
        if (users_.getUserById(id) == null){
            throw new EntityNotFoundException();
        }else {
            OtherUser user = users_.getUserById(id);
            users_.findAllUsers().remove(user);
            return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("find_user")
    public ResponseEntity<List<String>> searchUser(@RequestParam("find") String find) {
        List<String> list = new ArrayList<String>();

            for (int i = 0; i < users_.findAllUsers().size(); i++) {
                if(users_.findAllUsers().get(i).findUser(find)){
                    list.add("ID:"+users_.findAllUsers().get(i).getId()+" "+users_.findAllUsers().get(i).getUserName());
                }
            }
        if (list.isEmpty()) {
            list.add("User not found!");
            return new ResponseEntity<List<String>>(list, HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @GetMapping("{id}/find_entry")
    public ResponseEntity<List<String>> searchEntry(@PathVariable String id, @RequestParam("find") String find) throws EntityNotFoundException{
        List<String> list = new ArrayList<String>();
        List<PhoneBookEntry> phoneBooks= users_.getUserById(id).getPhoneBooks();

        for (int i = 0; i < phoneBooks.size(); i++) {
            if(phoneBooks.get(i).findNumber(find)){
                list.add(phoneBooks.get(i).getTitle()+" "+phoneBooks.get(i).getNumber());
            }
        }
        if (list.isEmpty()) {
            list.add("User not found!");
            return new ResponseEntity<List<String>>(list, HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @GetMapping("{id}/phonebook")
    public ResponseEntity<List<PhoneBookEntry>> getPhoneBook(@PathVariable String id) throws EntityNotFoundException{
        List<PhoneBookEntry> list = new ArrayList<>();
        OtherUser user = users_.getUserById(id);
        if (user.getPhoneBooks().isEmpty()){
            //list.add("PhoneBook is Empty!");
            return new ResponseEntity<List<PhoneBookEntry>>(list, HttpStatus.NO_CONTENT);
        }else{
            for (int i = 0; i < user.getPhoneBooks().size(); i++) {
                list.add(user.getPhoneBookEntry(i));
            }
            return new ResponseEntity<List<PhoneBookEntry>>(list, HttpStatus.OK);
        }
    }

    @GetMapping("{id}/phonebook/{entryId}")
    public ResponseEntity <PhoneBookEntry> getEntry(@PathVariable String id, @PathVariable int entryId) throws EntityNotFoundException {
        PhoneBookEntry phoneBookEntry = users_.getUserById(id).getPhoneBookEntry(entryId);
        return new ResponseEntity<PhoneBookEntry>(phoneBookEntry, HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<PhoneBookEntry> createEntry(@PathVariable String id,@Valid @RequestBody PhoneBookEntry entry) throws EntityNotFoundException{
        if(entry.getTitle()==null||entry.getNumber()==null) {
            throw new EntityNotFoundException();
        }else {
            users_.getUserById(id).addPhoneBookEntry(entry);
            return new ResponseEntity<PhoneBookEntry>(entry, HttpStatus.CREATED);
        }
    }

    @GetMapping("{id}/create")
    public ResponseEntity<PhoneBookEntry> createEntryParam(@PathVariable String id,
                                                           @RequestParam String title,
                                                           @RequestParam String number) throws EntityNotFoundException{
        PhoneBookEntry entry = new PhoneBookEntry(title, number);
        if(entry.getTitle()==null||entry.getNumber()==null) {
            throw new EntityNotFoundException();
        }else {
            users_.getUserById(id).addPhoneBookEntry(entry);
            return new ResponseEntity<PhoneBookEntry>(entry, HttpStatus.CREATED);
        }
    }

    @PutMapping("{id}/phonebook/{entryId}")
    public ResponseEntity<PhoneBookEntry> updateEntry(@PathVariable String id, @PathVariable int entryId, @RequestBody @Valid PhoneBookEntry entry) throws EntityNotFoundException{
        if(entry.getTitle()==null||entry.getNumber()==null) {
            throw new EntityNotFoundException();
        }else {
            OtherUser user = users_.getUserById(id);
            user.setPhoneBookEntry(entryId, entry);
            return new ResponseEntity<PhoneBookEntry>(entry, HttpStatus.OK);
        }
    }

    @GetMapping("{id}/phonebook/{entryId}/update")
    public ResponseEntity<PhoneBookEntry> updateEntryParam(@PathVariable String id,
                                                      @PathVariable int entryId,
                                                      @RequestParam String title,
                                                      @RequestParam String number) throws EntityNotFoundException{
        PhoneBookEntry entry = new PhoneBookEntry(title, number);
        if(entry.getTitle()==null||entry.getNumber()==null) {
            throw new EntityNotFoundException();
        }else {
            OtherUser user = users_.getUserById(id);
            user.setPhoneBookEntry(entryId, entry);
            return new ResponseEntity<PhoneBookEntry>(entry, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("{id}/phonebook/{entryId}")
    public ResponseEntity<HttpStatus> deleteEntry(@PathVariable String id, @PathVariable int entryId) throws EntityNotFoundException{
        OtherUser user = users_.getUserById(id);
        user.deletePhoneBookEntry(entryId);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
}
