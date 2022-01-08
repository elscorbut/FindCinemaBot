package com.bvg.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "T_DIRECTORY")
public class Directory {

    @Id
    @SequenceGenerator(name = "t_directory_id_seq_gen", sequenceName = "t_directory_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "t_directory_id_seq_gen")
    protected Long id;

    /** Значения справочника */
    @OrderBy("value")
    @Where(clause = "hidden = false")
    @OneToMany(mappedBy = "directory", fetch = FetchType.LAZY)
    public List<DirectoryValue> values = new ArrayList<>();

    /** Ключ справочника */
    @Column(name = "name", nullable = false)
    private String name;

    /** Наименование справочника (описание) */
    @Column(name = "display_name", nullable = false)
    private String displayName;

    /** Ссылка на родительский справочник */
    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Directory parent;
}
