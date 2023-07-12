import React from 'react'
import '../styles/TransactionsStyle.css'


export const TransactionItem = (props) => {
    return (
        <tr className='Default-Row-Table'>
            <td className="Default-Cell">{props.item["bankAccountSenderName"]}</td>
            <td className="Default-Cell">{props.item["bankAccountReceiverName"]}</td>
            <td className="Default-Cell">0</td>
            <td className="Default-Cell">0</td>
            <td className="Balance-Cell">-{props.item["balance"]} €</td>
        </tr>
    )
}