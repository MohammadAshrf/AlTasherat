package com.solutionplus.altasherat.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

//    lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding =  FragmentMainBinding.inflate(layoutInflater)

        //        logger.debug("This is a debug log....")
//        logger.info("This is an info log....")
//        logger.warning("This is a warning log....")
//        logger.error("This is an error log....")
//        logger.error("This is an error log with throwable....", Throwable("CustomException....."))
//
//        val logger = LoggerProvider.getLogger()
//        logger.log("Hello from ${BuildConfig.FLAVOR} flavor!")

        return null
    }

    companion object {
        private val logger = getClassLogger()
    }



    /**   Feature [data, domain , presentation]
     *   Data, Domain [mapper , models , repository "local, remote", interactor]

     * *login* user[gender = 0,1,2]

    Data:
    - mappers ->
    - repository -->
     *  *    - localDs  -> entity UserEntity
     *  *  - remoteDs  ->  retrofit
     *  - models--> [dto, entity]

    Domain :
    - interactor --> usecases [ emaillogin, phonelogin , socialLogin]
    - repository -->
    -localDs -->
    remoteDs -->
    models     -->  [domain]

    User[dender = enumGender()]

    UserFeature :
    - User Dto (Data Transfer Object) --> DTO
    - User Entity --> Entity
    - User --> Domain

     **/

}