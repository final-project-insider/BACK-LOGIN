package com.insider.login.commute.service;

import com.insider.login.commute.dto.CommuteDTO;
import com.insider.login.commute.dto.UpdateTimeOfCommuteDTO;
import com.insider.login.commute.entity.Commute;
import com.insider.login.commute.repository.CommuteRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CommuteService {

    private final CommuteRepository commuteRepository;

    private final ModelMapper modelMapper;

    public CommuteService(CommuteRepository commuteRepository, ModelMapper modelMapper) {
        this.commuteRepository = commuteRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void insertTimeOfCommute(CommuteDTO newCommute) {

        /* 방법 1 */

        log.info("[CommuteService] insertTimeOfCommute");
        log.info("[CommuteService] CommuteDTO : " + newCommute);
        try {
            Commute startWork = new Commute(
                    newCommute.getMemberId(),
                    newCommute.getWorkingDate(),
                    newCommute.getStartWork(),
                    newCommute.getEndWork(),
                    newCommute.getWorkingStatus(),
                    newCommute.getTotalWorkingHours()
            );

            log.info("[CommuteService] startwork :", startWork);

            commuteRepository.save(startWork);

        } catch (Exception e) {
            log.info("[insertCommute] Exception");
        }

        log.info("[CommuteService] insertTimeOfCommute End ===========");


        /* 방법 2 */
//        commuteRepository.save(modelMapper.map(newCommute, Commute.class));
    }


    @Transactional
    public Map<String, Object> updateTimeOfCommuteByCommuteNo(UpdateTimeOfCommuteDTO updateTimeOfCommute) {

        Map<String, Object> result = new HashMap<>();

        int commuteNo = updateTimeOfCommute.getCommuteNo();
        Commute commute = commuteRepository.findByCommuteNo(commuteNo);

        log.info("update 전 : " , commute);

        if(commute != null) {
            CommuteDTO commuteDTO = modelMapper.map(commute, CommuteDTO.class);

            commuteDTO.setEndWork(updateTimeOfCommute.getEndWork());
            commuteDTO.setWorkingStatus(updateTimeOfCommute.getWorkingStatus());
            commuteDTO.setTotalWorkingHours(updateTimeOfCommute.getTotalWorkingHours());

            Commute updateCommute = modelMapper.map(commuteDTO, Commute.class);
            commuteRepository.save(updateCommute);

            log.info("update 후 : ", commuteRepository.save(updateCommute));

            result.put("result", true);

        } else {
            result.put("result", false);
        }
        return result;
    }
}
