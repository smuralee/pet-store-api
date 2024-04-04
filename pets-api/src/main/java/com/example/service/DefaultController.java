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

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping
    public Map<String, String> getDefaultResponse(HttpServletRequest request) throws UnknownHostException {
        log.info("Default response for the root context of PetStore");
        HashMap<String, String> map = new HashMap<>();
        map.put("Status", "Success");
        map.put("Remote address", request.getRemoteAddr());
        map.put("Host name", InetAddress.getLocalHost().getHostName());
        return map;
    }
}
