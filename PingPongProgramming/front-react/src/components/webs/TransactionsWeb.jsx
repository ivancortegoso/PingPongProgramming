import React from 'react'
import '../styles/TransactionsStyle.css'
import {TransactionItem} from "../TransactionItem";

export class TransactionsWeb extends React.Component {

    constructor() {
        super();
        this.state = {
            list: [0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,56,7,8],
        }
        this.fetchTransactions = this.fetchTransactions.bind(this)
    }

    async fetchTransactions() {
        const response = await fetch("http://localhost:8080/api/transactions");
        const transactionList = await response.json();
        this.setState({list: transactionList});
    }

    componentDidMount() {
        this.fetchTransactions()
    }

    render() {
        return (
            <div className={"TransactionsWeb"}>
                <div className={"TransactionsWeb-List ShadowBox"}>
                    Transactions
                    {
                        this.state.list.map((item, index) => {
                            return(
                                <>
                                    {index == 0 ? "" : <div className={"HSeparator"}></div>}
                                    <TransactionItem item={item}/>
                                </>
                            )
                        })
                    }

                </div>
            </div>
        )
    }
}