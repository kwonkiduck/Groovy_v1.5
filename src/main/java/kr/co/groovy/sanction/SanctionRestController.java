package kr.co.groovy.sanction;

import kr.co.groovy.utils.ParamMap;
import kr.co.groovy.vo.EmployeeVO;
import kr.co.groovy.vo.SanctionBookmarkVO;
import kr.co.groovy.vo.SanctionLineVO;
import kr.co.groovy.vo.SanctionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/sanction/api")
public class SanctionRestController {
    final
    SanctionService service;

    public SanctionRestController(SanctionService service) {
        this.service = service;
    }

    /**
     * 전자 결재 승인 처리
     */

    @PutMapping("/approval/{emplId}/{etprCode}")
    public void approve(@PathVariable String emplId, @PathVariable String etprCode) throws SQLException {
        service.approve(emplId, etprCode);
    }

    @PutMapping("/final/approval/{emplId}/{etprCode}")
    public void finalApprove(@PathVariable String emplId, @PathVariable String etprCode) throws SQLException {
        service.finalApprove(emplId, etprCode);
    }

    @PutMapping("/reject")
    public void reject(@RequestBody Map<String, Object> map) throws SQLException {
        service.reject(map);
    }

    @PutMapping("/collect/{etprCode}")
    public void collect(@PathVariable String etprCode) throws SQLException {
        service.collect(etprCode);
    }


    /**
     * 전자 결재 시작
     */

    /* 전자결재 후처리 실행 */
    @PostMapping("/reflection")
    public void startApprove(@RequestBody Map<String, Object> request) {
        service.startApprove(request);
    }

    /* 결재선 포함 결재 문서 내용 insert */
    @PostMapping("/sanction")
    public void inputSanction(@RequestBody ParamMap requestData) throws IOException, SQLException {
        service.inputSanction(requestData);
    }

    /**
     * 결재함 결재 문서 리스트
     */

    @GetMapping("/status")
    public String getStatus(String emplId, String progrs) throws SQLException {
        return String.valueOf(service.getStatus(emplId, progrs));
    }

    @GetMapping("/request/{emplId}")
    public List<SanctionVO> loadRequest(@PathVariable String emplId) throws SQLException {
        return service.loadRequest(emplId);
    }

    @GetMapping("/awaiting/{emplId}")
    public List<SanctionLineVO> loadAwaiting(@PathVariable String emplId) throws SQLException {
        return service.loadAwaiting(emplId);
    }

    @GetMapping("/reference/{emplId}")
    public List<SanctionVO> loadReference(@PathVariable String emplId) throws SQLException {
        return service.loadReference(emplId);
    }


    /**
     * 결재선 지정 및 즐겨찾기
     */

    @GetMapping("/line/{emplId}")
    public List<EmployeeVO> loadAllLine(@PathVariable String emplId, @RequestParam(required = false, defaultValue = "") String keyword) throws SQLException {
        return service.loadAllLine(emplId, keyword);
    }

    @PostMapping("/bookmark")
    public void inputBookmark(@RequestBody SanctionBookmarkVO vo) throws SQLException {
        service.inputBookmark(vo);
    }

    @GetMapping("/bookmark/{emplId}")
    public List<Map<String, String>> loadBookmark(@PathVariable String emplId) throws SQLException {
        return service.loadBookmark(emplId);
    }

    @DeleteMapping("/bookmark/{sn}")
    public void deleteBookmark(@PathVariable String sn) throws SQLException {
        service.deleteBookmark(sn);
    }

}
