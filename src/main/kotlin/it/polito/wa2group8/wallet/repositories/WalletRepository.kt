package it.polito.wa2group8.wallet.repositories

import it.polito.wa2group8.wallet.domain.Wallet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository : CrudRepository<Wallet,Long>{

}