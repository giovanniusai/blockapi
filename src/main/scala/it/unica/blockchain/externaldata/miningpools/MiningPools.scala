package it.unica.blockchain.externaldata.miningpools

import it.unica.blockchain.blockchains.bitcoin.BitcoinTransaction
import it.unica.blockchain.blockchains.litecoin.LitecoinTransaction

object MiningPools {

  def getBitcoinPool(transaction: BitcoinTransaction): String = {

    if (transaction.inputs.head.isCoinbase) {
      val programByte: Array[Byte] = transaction.inputs.head.inScript.getProgram()
      if (programByte != null) {
        val hex: String = programByte.map("%02x".format(_)).mkString
        if (hex != "") {
          // Known pool codes listed on blockchain.info
          // See https://github.com/blockchain/Blockchain-Known-Pools/blob/master/pools.json for more
          if (hex.contains("35304254432e636f6d")) return Pools._50BTC
          if (hex.contains("416e74506f6f6c3")) return Pools.ANTPOOL
          if (hex.contains("42697446757279")) return Pools.BITFURY
          if (hex.contains("736c757368")) return Pools.SLUSHPOOL
          if (hex.contains("42544343")) return Pools.BTCCPOOL
          if (hex.contains("4254432e434f4d")) return Pools.BTCCOM
          if (hex.contains("566961425443")) return Pools.VIABTC
          if (hex.contains("4254432e544f502")) return Pools.BTCTOP
          if (hex.contains("426974436c7562204e6574776f726b")) return Pools.BITCLUBNETWORK
          if (hex.contains("67626d696e657273")) return Pools.GBMINERS
          if (hex.contains("42697466757279")) return Pools.BITFURY
          if (hex.contains("4269744d696e746572"))  return Pools.BITMINTER
          if (hex.contains("4b616e6f")) return Pools.KANOPOOL
          if (hex.contains("426974636f696e2d5275737369612e7275")) return Pools.BITCOINRUSSIA
          if (hex.contains("426974636f696e2d496e646961")) return Pools.BITCOININDIA
          if (hex.contains("42570a665704") ||
            hex.contains("2f42572f") ||
            hex.contains("425720506f6f6c")) return Pools.BW
          if (hex.contains("3538636f696e2e636f6d")) return Pools._58COIN
          if (hex.contains("706f6f6c2e626974636f696e2e636f6d")) return Pools.BITCOINCOM
          if (hex.contains("436f6e6e656374425443202d20486f6d6520666f72204d696e657273")) return Pools.CONNECTED
          // F2Pool does not have a unique identifier
          if (hex.contains("777868") ||
            hex.contains("66326261636b7570") ||
            hex.contains("68663235") ||
            hex.contains("73796a756e303031") ||
            hex.contains("716c7339") ||
            hex.contains("687578696e6767616f7a68616f") ||
            //general pattern for F2POOL
            ( hex.contains("f09f90") && hex.contains("4d696e656420627920") )
          ) return Pools.F2POOL

          if (hex.contains("566961425443")) return Pools.VIABTC
          if (hex.contains("2e7a706f6f6c2e6361")) return Pools.ZPOOL
          if (hex.contains("2f6d70682f")) return Pools.MPH
          if (hex.contains("544244696365")) return Pools.TBDICE
          if (hex.contains("474956452d4d452d434f494e532e636f6d")) return Pools.GIVEMECOINS
          if (hex.contains("436f696e4d696e65")) return Pools.COINMINE
          if (hex.contains("424154504f4f4c")) return Pools.BATPOOL

          /**Searching for most complex pattern after every other known pattern due to avoid
            * useless operations and minimize collision risk
            */

          if(hex.matches(".*2f434d.{16}2f.*")) return Pools.CLEVERMINING
          if(hex.matches(".*4d696e656420627920416e74506f6f6c.*")) return Pools.ANTPOOL
          if(hex.contains("2f50726f68617368696e672f")) return Pools.PROHASHING
          //if it isn't a generic nodeStratum signature
          if((!hex.contains("6e6f64655374726174756d")) && hex.matches(".*2f[a-f||0-9]{20}2f"))  return Pools.PROHASHING
          //then if it matches this regex it's a block mined by PH
        }
      }
    }

    return Pools.UNKNOWN
  }


  def getEthereumPool(extraData: String): String = {
    if (extraData.contains("7777772e62772e636f6d")) return Pools.BW
    if (extraData.contains("65746865726d696e652d")) return Pools.ETHERMINE
    if (extraData.contains("e4b883e5bda9e7a59ee4bb99e9b1bc")) return Pools.F2POOL2
    if (extraData.contains("4554482e45544846414e532e4f52472d")) return Pools.SPARKPOOL
    if (extraData.contains("6e616e6f706f6f6c2e6f7267")) return Pools.NANOPOOL
    if (extraData.contains("4477617266506f6f6c")) return Pools.DWARF
    if (extraData.contains("7869786978697869")) return Pools.BITCLUBPOOL
    if (extraData.contains("657468706f6f6c2d")) return Pools.ETHPOOL2
    if (extraData.contains("70616e64615f706f6f6c")) return Pools.PANDAPOOL
    if (extraData.contains("786e706f6f6c2e636e")) return Pools.XNPOOL
    if (extraData.contains("7575706f6f6c2e636e")) return Pools.UUPOOL
    if (extraData.contains("7761746572686f6c652e696f")) return Pools.WATERHOLE
    if (extraData.contains("5553492054656368")) return Pools.USI
    if (extraData.contains("7777772e627463632e72656e")) return Pools.BTCC
    if (extraData.contains("457468657265756d50504c4e532f326d696e657273")) return Pools.PPLNS
    if (extraData.contains("4f70656e457468657265756d506f6f6c2f74776574682e7477")) return Pools.TWETH
    if (extraData.contains("d5830107068650617269747986312e32302e30826c69")) return Pools.COINOTRON2

    return Pools.UNKNOWN
  }

  /** Antpool, BTCChina, ViaBTC, BW and F2Pool mine LTC too.
    * LTCTOP, LitecoinPool, BW and some F2Pool identifiers
    * found
    */
  def getLitecoinPool(transaction: LitecoinTransaction): String = {

    if (transaction.inputs.head.isCoinbase) {
      val programByte: Array[Byte] = transaction.inputs.head.inScript.getProgram()

      if (programByte != null) {
        val hex: String = programByte.map("%02x".format(_)).mkString
        if (hex != "") {
          if (hex.contains("2f4c502f")) return Pools.LITECOINPOOL
          if (hex.contains("42544343")) return Pools.BTCCPOOL
          if (hex.contains("4c54432e544f50")) return Pools.LTCTOP
          if (hex.contains("566961425443")) return Pools.VIABTC
          if (hex.contains("2e7a706f6f6c2e6361")) return Pools.ZPOOL
          if (hex.contains("2f6d70682f")) return Pools.MPH
          if (hex.contains("544244696365")) return Pools.TBDICE
          if (hex.contains("474956452d4d452d434f494e532e636f6d")) return Pools.GIVEMECOINS
          if (hex.contains("436f696e4d696e65")) return Pools.COINMINE
          if (hex.contains("424154504f4f4c")) return Pools.BATPOOL

          if (hex.contains("4d696e656420627920416e74506f6f6c20") || hex.matches(".*4d696e656420627920416e74506f6f6c.*")) return Pools.ANTPOOL
          if (hex.contains("42570a665704") ||
            hex.contains("2f42572f") ||
            hex.contains("425720506f6f6c")) return Pools.BW
          // F2Pool does not have a unique identifier
          // Found a pattern for F2Pool
          if (( hex.contains("f09f90") && hex.contains("4d696e656420627920") )
          ) return Pools.F2POOL


          /**Searching for most complex pattern after every other known pattern due to avoid
            * useless operations and minimize collision risk
            */

          if(hex.matches(".*2f434d.{12,22}2f.*")) return Pools.CLEVERMINING

          //PROHASHING SIGNATURE FOR REALLY OLD BLOCKS
          //SEPARATED DUE TO AVOID REGEX EXECUTION ON EVERY TRY
          if(hex.contains("2f50726f68617368696e672f")) return Pools.PROHASHING
          //if it isn't a generic nodeStratum signature
          if((!hex.contains("6e6f64655374726174756d")) && hex.matches(".*2f[a-f||0-9]{20}2f"))  return Pools.PROHASHING
          //then if it matches this regex it's a block mined by PH
        }
      }
    }
    return Pools.UNKNOWN
  }

}
