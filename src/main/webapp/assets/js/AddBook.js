function CustomValidation(input) {
    this.invalidities = [];
    this.validityChecks = [];

    //add reference to the input node
    this.inputNode = input;

    //trigger method to attach the listener
    this.registerListener();
}

CustomValidation.prototype = {
    addInvalidity: function(message) {
        this.invalidities.push(message);
    },
    getInvalidities: function() {
        return this.invalidities.join('. \n');
    },
    checkValidity: function(input) {
        for ( var i = 0; i < this.validityChecks.length; i++ ) {

            var isInvalid = this.validityChecks[i].isInvalid(input);
            if (isInvalid) {
                this.addInvalidity(this.validityChecks[i].invalidityMessage);
            }

            var requirementElement = this.validityChecks[i].element;

            if (requirementElement) {
                if (isInvalid) {
                    requirementElement.classList.add('invalid');
                    requirementElement.classList.remove('valid');
                } else {
                    requirementElement.classList.remove('invalid');
                    requirementElement.classList.add('valid');
                }

            } // end if requirementElement
        } // end for
    },
    checkInput: function() { // checkInput now encapsulated

        this.inputNode.CustomValidation.invalidities = [];
        this.checkValidity(this.inputNode);

        if ( this.inputNode.CustomValidation.invalidities.length === 0 && this.inputNode.value !== '' ) {
            this.inputNode.setCustomValidity('');
        } else {
            var message = this.inputNode.CustomValidation.getInvalidities();
            this.inputNode.setCustomValidity(message);
        }
    },
    registerListener: function() { //register the listener here

        var CustomValidation = this;

        this.inputNode.addEventListener('keyup', function() {
            CustomValidation.checkInput();
        });


    }

};


var titleValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 3;
        },
        invalidityMessage: 'This input needs to be at least 3 characters',
        element: document.querySelector('label[for="title"] .input-requirements li:nth-child(1)')
    }
];

var authorValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 4 ;
        },
        invalidityMessage: 'This input needs to be least 4 ',
        element: document.querySelector('label[for="author"] .input-requirements li:nth-child(1)')
    }
];

var editionValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 4 ;
        },
        invalidityMessage: 'This input needs to be least 4 ',
        element: document.querySelector('label[for="edition"] .input-requirements li:nth-child(1)')
    }
];

var editionDateValidityChecks = [
    {
        isInvalid: function(input) {
            var today =new Date().getTime();
            return (today - input) <= 0 ? true : false;
        },
        invalidityMessage: 'This input needs to be filled',
        element: document.querySelector('label[for="edition-date"] .input-requirements li:nth-child(1)')
    }
];

var creditDaysValidityChecks = [
    {
        isInvalid: function(input) {
            return input>0;
        },
        invalidityMessage: 'This input needs to be positive',
        element: document.querySelector('label[for="credit-days"] .input-requirements li:nth-child(1)')
    }
];

var amountValidityChecks = [
    {
        isInvalid: function(input) {
            return input>0;
        },
        invalidityMessage: 'This input needs to be positive',
        element: document.querySelector('label[for="amount"] .input-requirements li:nth-child(1)')
    }
];

/* ----------------------------
	Setup CustomValidation
	Setup the CustomValidation prototype for each input
	Also sets which array of validity checks to use for that input
---------------------------- */

var titleInput = document.getElementById('title');
var authorInput = document.getElementById('author');
var editionInput = document.getElementById('edition');
var editionDateInput = document.getElementById('edition-date');
var creditDaysInput = document.getElementById('credit-days');
var amountInput = document.getElementById('amount');

titleInput.CustomValidation = new CustomValidation(titleInput);
titleInput.CustomValidation.validityChecks = titleValidityChecks;

authorInput.CustomValidation = new CustomValidation(authorInput);
authorInput.CustomValidation.validityChecks = authorValidityChecks;

editionInput.CustomValidation = new CustomValidation(editionInput);
editionInput.CustomValidation.validityChecks = editionValidityChecks;

editionDateInput.CustomValidation = new CustomValidation(editionDateInput);
editionDateInput.CustomValidation.validityChecks = editionDateValidityChecks;

creditDaysInput.CustomValidation = new CustomValidation(creditDaysInput);
creditDaysInput.CustomValidation.validityChecks = creditDaysValidityChecks;

amountInput.CustomValidation = new CustomValidation(amountInput);
amountInput.CustomValidation.validityChecks = amountValidityChecks;

/* ----------------------------
	Event Listeners
---------------------------- */

var inputs = document.querySelectorAll('input:not([type="submit"])');


var submit = document.querySelector('input[type="submit"');
var form = document.getElementById('addBook');

function validate() {
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].CustomValidation.checkInput();
    }
}

submit.addEventListener('click', validate);
form.addEventListener('submit', validate);


