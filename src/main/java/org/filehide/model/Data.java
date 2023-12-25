package org.filehide.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Data {
    @NonNull
    private int id;
    @NonNull
    private String fileName;
    @NonNull
    private String path;
    private String email;
}
