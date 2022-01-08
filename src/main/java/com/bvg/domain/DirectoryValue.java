package com.bvg.domain;

import com.bvg.type.IDirectoryValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "T_DIRECTORY_VALUE")
public class DirectoryValue implements IDirectoryValue {

    @Id
    @SequenceGenerator(name = "t_directory_value_id_seq_gen", sequenceName = "t_directory_value_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "t_directory_value_id_seq_gen")
    protected Long id;

    /** Ссылка на справочник*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "directory")
    private Directory directory;

    /** Ключ справочного значения */
    @Column
    private String key;

    /** Справочное значение (для вывода на интерфейс)*/
    @Column
    private String value;

    /** Сокращенное справочное значение (для вывода на интерфейс)*/
    @Column(name = "short_value")
    private String shortValue;

    /** Скрытое значение (true = не показывать значение на интерфейсе)*/
    @Column
    private boolean hidden;

    /** Приоритет (порядок отображения на интерфейсе)*/
    @Column(columnDefinition = "integer default 0")
    private Integer priority;

    /** Ссылка на родительское справочное значение*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private DirectoryValue parent;

    @Override
    public Long getParentId() {
        return parent != null ? parent.getId() : null;
    }

    @Override
    public Long getDirectoryId() {
        return directory != null ? directory.getId() : null;
    }
}
