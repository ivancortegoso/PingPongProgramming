import React from 'react'
import './styles/TransactionsStyle.css'


export class TransactionItem extends React.Component {

    render() {
        return (
            <div className={"TransactionItem Space-Between"}>
                <div>
                    <div>X paid Y</div>
                    <div>Payment: X to Y</div>
                    <div>Like - Comment</div>
                </div>
                <div>
                    <label className={"Balance"}>-1000€</label>
                </div>
            </div>
        )
    }
}