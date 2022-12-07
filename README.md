# ikola : [Inuka](https://inukacoaching.com/?utm_source=Github&utm_medium=link&utm_campaign=Github+traffic "Inuka") Object Level Authorization

# How to use
## Add maven dependency
     <dependency>
           <groupId>io.inuka</groupId>
           <artifactId>ikola</artifactId>
           <version>1.0</version>
     </dependency>
## Configure Object Level Security
Object Level Authorization rules are specific to each application. ikola provide a flexible API to implement and inject your own logic.
### 1. Detecting the logged in user
You need to provide an implementation for `AuthenticatedUserIdResolver` interface.
`resolveUserId` method receives an instance of `org.springframework.security.core.Authentication` token and should return the desired User object or just Id of the user.

    public interface AuthenticatedUserIdResolver {
        Object resolveUserId(Authentication var1);
    }
here is a sample implementation for Spring Pet Clinic project:

    public class JdbcAuthenticatedUserIdResolver implements AuthenticatedUserIdResolver {
        private UserService userService;
    
        public JdbcAuthenticatedUserIdResolver(UserService userService) {
            this.userService = userService;
        }
    
        @Override
        public Object resolveUserId(Authentication authentication) {
            Object principal = authentication.getPrincipal();
            if (principal != null) {
                User userDetails = (User) principal;
                return userService.getByUsername(userDetails.getUsername());
            }
            return null;
        }
    }
### 2. Detecting the Object Owner
You can use ikola `LambdaExpressionObjectOwnerResolver` to let ikola find the Owner (or just the ownerId) for any desired Object.

    LambdaExpressionObjectOwnerResolver()
                    .setObjectOwnerIdResolver(Pet.class,(o) ->  ((Pet)o).getOwner().getId())
					 .setObjectOwnerIdResolver(org.springframework.samples.petclinic.rest.dto.PetDto.class,(o) ->  ((org.springframework.samples.petclinic.rest.dto.PetDto)o).getOwnerId();));
### 3. Defining non-owner authorized users
You can define who has access to the Object (other that it's owner)

### Add a new Configuration class that extends `io.inuka.ikola.ObjectLevelSecurityConfigure`

# Inuka Coaching
Our goal is to reduce and prevent too much stress and amongst all people, by applying a simple and effective coaching method. [Read more about how Inuka works](https://inukacoaching.com/?utm_source=Github&utm_medium=link&utm_campaign=Github+traffic "Read more about how Inuka works")