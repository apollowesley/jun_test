/*
 * Custom validators for VeeValidate
 */
import {http} from '../resources'

export default {

  // Server side validation
  remote: {
    getMessage: (field) => `${field} already exists or it's not valid.`,
    // If you want to return a promise you will have to make sure it always resolves
    // to an object containing a 'valid' property which is a boolean state of the validation.
    validate (val, args) {
      return http.get(args[0], {
        params: {
          [args[1]]: val
        }
      }).then(response => response.data)
    }
  },

  unique: {
    getMessage: (field) => `${field} must be unique.`,
    // If you want to return a promise you will have to make sure it always resolves
    // to an object containing a 'valid' property which is a boolean state of the validation.
    validate: (val, args, field) => {
      return false
    }
  }
}
