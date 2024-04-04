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
import com.example.errors.DataNotFoundException;
import com.example.repository.PetsRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.util.Constants.*;

@Slf4j
@RestController
@RequestMapping("/pets")
public class PetsController {

    private final PetsRepository repository;

    private final MeterRegistry meterRegistry;

    public PetsController(PetsRepository repository, MeterRegistry meterRegistry) {
        this.repository = repository;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping
    public List<Pet> getAll() {
        log.info("Getting all the pets");
        meterRegistry.counter(PETS_GET_ALL_INVOCATIONS).increment();
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Pet getById(final @PathVariable Long id) {
        log.info("Getting the pets by id");
        meterRegistry.counter(PETS_GET_BY_ID_INVOCATIONS).increment();
        Optional<Pet> pet = this.repository.findById(id);
        return pet.orElseThrow(DataNotFoundException::new);
    }

    @PostMapping
    public Pet addPet(final @RequestBody Pet pet) {
        log.info("Saving the new pet");
        meterRegistry.counter(PETS_POST_INVOCATIONS).increment();
        return this.repository.save(pet);
    }

    @PutMapping("/{id}")
    public Pet updatePet(final @RequestBody Pet pet, final @PathVariable Long id) {
        log.info("Fetching the pet by id");
        meterRegistry.counter(PETS_UPDATE_INVOCATIONS).increment();
        Optional<Pet> fetchedPet = this.repository.findById(id);

        log.info("Updating the pet identified by the id");
        if (fetchedPet.isPresent()) {
            pet.setId(id);
            return this.repository.save(pet);
        } else {
            throw new DataNotFoundException();
        }
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting the pet by id");
        meterRegistry.counter(PETS_DELETE_INVOCATIONS).increment();
        this.repository.deleteById(id);
    }
}
