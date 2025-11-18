package nhatm.project.demo.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingResult<T> {

    private Collection<T> result;
    private int totalPages;
    private int totalElements;
    private int size;
    private int page;
    private boolean empty;
}
