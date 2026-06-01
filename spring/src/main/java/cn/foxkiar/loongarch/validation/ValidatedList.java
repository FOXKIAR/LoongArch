package cn.foxkiar.loongarch.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValidatedList<T> implements List<T> {
    @Valid
    @Delegate
    private List<T> data = new ArrayList<>();
}
