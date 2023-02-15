package com.gustavomoura.softdesign.client;

import com.gustavomoura.softdesign.dto.CpfEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "cpfClient", url = "https://user-info.herokuapp.com/users")
public interface CpfClient {

    @RequestMapping(path = "/{cpf}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CpfEntity verifyCpf(@PathVariable("cpf") String cpf);
}
