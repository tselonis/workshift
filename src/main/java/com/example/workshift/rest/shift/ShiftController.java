package com.example.workshift.rest.shift;

import com.example.workshift.business.shift.Shift;
import com.example.workshift.business.shift.ShiftService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shifts")
@AllArgsConstructor
public class ShiftController {

    private ShiftDomainMapper shiftDomainMapper;
    private ShiftService shiftService;

    @PostMapping
    public ResponseEntity<Long> createShift(@RequestBody final CreateShiftRequestDto createShiftRequestDto) {
        Shift createdShift = shiftService.createShift(shiftDomainMapper.mapToCreateShiftRequest(createShiftRequestDto));
        return ResponseEntity.ok(createdShift.id());
    }
}
