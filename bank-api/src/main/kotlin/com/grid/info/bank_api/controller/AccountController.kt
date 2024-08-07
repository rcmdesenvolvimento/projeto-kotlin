package com.grid.info.bank_api.controller

import com.grid.info.bank_api.model.Account
import com.grid.info.bank_api.repository.AccountRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("account")
class AccountController(private val repository: AccountRepository) {

    @PostMapping
    fun create(@RequestBody account: Account): Account = repository.save(account);

    @GetMapping()
    fun getAll(): List<Account> = repository.findAll();

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Account> =
        this.repository.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build());

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody account: Account): ResponseEntity<Account> =
        this.repository.findById(id).map {
            val accountToUpdate = account.copy(
                name = account.name,
                document = account.document,
                phone = account.phone
            )
            ResponseEntity.ok(this.repository.save(accountToUpdate))
        }.orElse(ResponseEntity.notFound().build());

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
        this.repository.findById(id).map {
            this.repository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build());
}
