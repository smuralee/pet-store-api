/**
 * Copyright 2024 Suraj Muraleedharan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.service;

import com.example.entity.Pet;
import com.example.repository.PetsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
class PetsControllerTest {

    @Spy
    private final ObjectMapper mapper = new ObjectMapper();

    @Spy
    private List<Pet> list;

    @MockBean
    private MeterRegistry registry;

    @MockBean
    private PetsRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        list = Arrays.asList(
                new Pet(1L, "Tom", "German Shepherd", 1, false),
                new Pet(2L, "Emily", "Siberian Husky", 2, false),
                new Pet(3L, "Catherine", "Siamese Cat", 3, false),
                new Pet(4L, "Richard", "Persian Cat", 4, false)
        );
        Counter counter = mock(Counter.class);
        when(registry.counter(any(String.class))).thenReturn(counter);
    }

    @Test
    void getAll() throws Exception {

        when(repository.findAll()).thenReturn(list);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/pets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(list))
                );

        // Verify the method is called just once
        verify(repository, times(1)).findAll();

    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void getById(final Long selectedId) throws Exception {

        Optional<Pet> pet = list.stream()
                .filter(item -> item.getId().equals(selectedId))
                .findFirst();

        when(repository.findById(Mockito.anyLong())).thenReturn(pet);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/pets/".concat(String.valueOf(selectedId)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(pet.orElse(null)))
                );

        // Verify the method is called just once
        verify(repository, times(1)).findById(Mockito.anyLong());

    }

    @Test
    void addPet() throws Exception {

        // Payload for the REST endpoint
        Pet payload = new Pet();
        payload.setName("Tom");
        payload.setDescription("German Shepherd");
        payload.setAge(1);

        // Response for the REST endpoint
        Pet response = new Pet();
        response.setId(1L);
        response.setName("Tom");
        response.setDescription("German Shepherd");
        response.setAge(1);

        when(repository.save(Mockito.any(Pet.class))).thenReturn(response);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/pets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(response))
                );

        // Verify the method is called just once
        verify(repository, times(1)).save(Mockito.any(Pet.class));

    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void updatePet(final Long selectedId) throws Exception {

        Optional<Pet> pet = list.stream()
                .filter(item -> item.getId().equals(selectedId))
                .findFirst();

        when(repository.findById(Mockito.anyLong())).thenReturn(pet);

        // Payload for the REST endpoint
        Pet payload = new Pet();
        payload.setName("Tom");
        payload.setDescription("German Shepherd");
        payload.setAge(1);

        // Response for the REST endpoint
        Pet response = new Pet();
        response.setId(1L);
        response.setName("Tom");
        response.setDescription("German Shepherd");
        response.setAge(1);

        when(repository.save(Mockito.any(Pet.class))).thenReturn(response);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/pets/".concat(String.valueOf(selectedId)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(this.mapper.writeValueAsString(response))
                );

        // Verify the method is called just once
        verify(repository, times(1)).save(Mockito.any(Pet.class));
        verify(repository, times(1)).findById(Mockito.anyLong());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void deleteById(final Long selectedId) throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/pets/".concat(String.valueOf(selectedId)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        // Verify the method is called just once
        verify(repository, times(1)).deleteById(Mockito.anyLong());

    }
}
