package com.orbitech.npvet.ControllerTest;


import com.orbitech.npvet.Controller.AnimalController;
import com.orbitech.npvet.DTO.AnimalDTO;
import com.orbitech.npvet.DTO.TutorDTO;
import com.orbitech.npvet.Entity.Animal;
import com.orbitech.npvet.Entity.Tutor;
import com.orbitech.npvet.Repository.AnimalRepository;
import com.orbitech.npvet.Repository.TutorRepository;
import com.orbitech.npvet.Service.AnimalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class AnimalControllerTest {

    @MockBean
    private AnimalRepository repository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private AnimalService service;

    @InjectMocks
    private AnimalController controller;

    @Mock
    private ModelMapper modelMapper;

    private AnimalDTO animalDTO = new AnimalDTO();
    private Animal animal = new Animal();

    private TutorDTO tutorDTO = new TutorDTO();

    private Tutor tutor = new Tutor();


    @BeforeEach
    void SetUP(){
        tutorDTO.setId(2L);
        tutorDTO.setNome("Alice");
        tutorDTO.setCpf("123");

        tutor.setId(2L);
        tutor.setNome("Alice");
        tutor.setCpf("123");

        animal.setNome("toto");
        animal.setRaca("Cachorro");
        animal.setEspecie("Cachorro");
        animal.setIdade(10);
        animal.setPelagem("baixa");
        animal.setProcedencia("Duvidosa");
        animal.setPeso(10.50);
        animal.setTutor_id(tutor);

        animalDTO.setNome("toto");
        animalDTO.setRaca("Cachorro");
        animalDTO.setEspecie("Cachorro");
        animalDTO.setIdade(10);
        animalDTO.setPelagem("baixa");
        animalDTO.setProcedencia("Duvidosa");
        animalDTO.setPeso(10.50);
        animalDTO.setTutor_id(tutorDTO);


        List<Animal> animalList = new ArrayList<>();
        animalList.add(animal);

        when(tutorRepository.findById(2L)).thenReturn(Optional.of(tutor));


        when(repository.findById(1L)).thenReturn(Optional.of(animal));
        when(repository.findAll()).thenReturn(animalList);
        when(repository.findAllByEspecieLike("Cachorro")).thenReturn(animalList);
        when(repository.findAllByRacaLike("Cachorro")).thenReturn(animalList);
        when(repository.findAllByNomeLike("toto")).thenReturn(animalList);

        when(repository.save(animal)).thenReturn(animal);


        when(modelMapper.map(animal, AnimalDTO.class)).thenReturn(animalDTO);
        when(modelMapper.map(animalDTO, Animal.class)).thenReturn(animal);


    }

    @Test
    void getByIdTest(){
        ResponseEntity<AnimalDTO> response = controller.getById(1L);
        assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllTest(){
        ResponseEntity<List<AnimalDTO>> response = controller.getAll();
        assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        List<AnimalDTO> responseBody = response.getBody();
        assertEquals(1, responseBody.size());
    }

    @Test
    void getByNome(){
        ResponseEntity<List<AnimalDTO>> response = controller.getByNome("toto");
        assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        List<AnimalDTO> responseBody = response.getBody();
        assertEquals(1, responseBody.size());
    }

    @Test
    void getByRaca(){
        ResponseEntity<List<AnimalDTO>> response = controller.getByRaca("Cachorro");
        assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        List<AnimalDTO> responseBody = response.getBody();
        assertEquals(1, responseBody.size());
    }

    @Test
    void getByEspecie(){
        ResponseEntity<List<AnimalDTO>> response = controller.getByEspecie("Cachorro");
        assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        List<AnimalDTO> responseBody = response.getBody();
        assertEquals(1, responseBody.size());
    }

    @Test
    void create(){
        ResponseEntity<AnimalDTO> response = controller.create(animalDTO);
        assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void update(){
        ResponseEntity<AnimalDTO> response = controller.update(1L, animalDTO);
        assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }







}
