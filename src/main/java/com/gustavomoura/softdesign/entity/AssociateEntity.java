package com.gustavomoura.softdesign.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "associate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociateEntity {

    @Id
    @Column(name = "associate_id")
    private String id;

    private String cpf;
}
