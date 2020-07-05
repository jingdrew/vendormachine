package com.vm.xyz.app.model;

import com.vm.xyz.app.entity.Machine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResult {
    private List<Changes> changesList;
    private Machine machine;
}
