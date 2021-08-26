

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


var loginValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 3;
        },
        invalidityMessage: 'This input needs to be at least 3 characters',
        element: document.querySelector('label[for="login"] .input-requirements li:nth-child(1)')
    }
];

var passwordValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 4 ;
        },
        invalidityMessage: 'This input needs to be least 4 ',
        element: document.querySelector('label[for="password"] .input-requirements li:nth-child(1)')
    }
];

var mailValidityChecks = [
    {
        isInvalid: function(input) {
            return input.value.length < 4 ;
        },
        invalidityMessage: 'This input needs to be least 4 ',
        element: document.querySelector('label[for="mail"] .input-requirements li:nth-child(1)')
    }
];

/* ----------------------------
	Setup CustomValidation
	Setup the CustomValidation prototype for each input
	Also sets which array of validity checks to use for that input
---------------------------- */

var loginInput= document.getElementById('login');
var passwordInput = document.getElementById('password');
var mailInput = document.getElementById('email');

loginInput.CustomValidation = new CustomValidation(loginInput);
loginInput.CustomValidation.validityChecks = loginValidityChecks;

passwordInput.CustomValidation = new CustomValidation(passwordInput);
passwordInput.CustomValidation.validityChecks = passwordValidityChecks;

mailInput.CustomValidation = new CustomValidation(mailInput);
mailInput.CustomValidation.validityChecks = mailValidityChecks;

/* ----------------------------
	Event Listeners
---------------------------- */

var inputs = document.querySelectorAll('input:not([type="submit"])');


var submit = document.querySelector('input[type="submit"');
var form = document.getElementById('addLibrarian');

function validate() {
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].CustomValidation.checkInput();
    }
}

submit.addEventListener('click', validate);
form.addEventListener('submit', validate);


