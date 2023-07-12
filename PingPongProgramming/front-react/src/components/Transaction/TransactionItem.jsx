import React from 'react'
import '../styles/TransactionsStyle.css'


export const TransactionItem = (props) => {
    return (
        <div className='TransactionItem Space-Between'>
            <div>
                <div>{props.item["senderID"]} paid {props.item["receiverID"]}</div>
                <div>Likes:{props.item["likes"]} - Comments:{props.item["commentaries"].length}</div>
            </div>
            <label className={"Balance"}>-{props.item["balance"]} €</label>
        </div>
    )
}