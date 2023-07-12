import React from 'react'
import '../styles/TransactionsStyle.css'


export const TransactionItem = () => {
    return (
        <div className='TransactionItem Space-Between'>
            <div>
                <div>{this.props.item["senderID"]} paid {this.props.item["receiverID"]}</div>
                <div>Likes:{this.props.item["likes"]} - Comments:{this.props.item["commentaries"].length}</div>
            </div>
            <label className={"Balance"}>-{this.props.item["balance"]} €</label>
        </div>
    )
}